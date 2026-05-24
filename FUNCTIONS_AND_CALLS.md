# Functions, Calls, Parameters, And Stack Frames

This file explains how functions move through the Prev26 compiler: where they
are recognized, how parameters and calls are checked, how stack frames are laid
out, how static links work, and how a call becomes final assembly.

Use this together with `COMPILER_PHASES.md`: that file tells you what each phase
is for, while this file zooms in on one feature family.

## The Short Version

For a function call like:

```prev26
fun f(x : int, y : int) : int = x + y

fun main() : int =
    f(2, 3)
```

the compiler eventually implements the call like this:

```text
caller:
  store static link at SP + 0
  store first argument at SP + 8
  store second argument at SP + 16
  jump-and-link to f
  load return value from SP + 0

callee:
  build its own frame
  set FP so FP points back to the caller's call record
  read static link from FP + 0
  read first parameter from FP + 8
  read second parameter from FP + 16
  compute result
  store result to FP + 0
  restore frame and return
```

The important trick is that `SP + 0` in the caller becomes `FP + 0` in the
callee. That slot first contains the static link and later contains the return
value.

## Important Files

- Syntax and AST construction:
  `prev26/src/prev26lang/phase/synan/Prev26Parser.g4`
- AST node classes:
  `prev26/src/prev26lang/phase/abstr/AST.java`
- Name and type checking:
  `prev26/src/prev26lang/phase/seman/NameResolver.java`,
  `TypeConstructor.java`, `TypeChecker.java`
- Stack-frame and parameter layout:
  `prev26/src/prev26lang/phase/memory/Layouter.java`
- Intermediate code for calls and static links:
  `prev26/src/prev26lang/phase/imrgen/ImrGenerator.java`
- Call canonicalization:
  `prev26/src/prev26lang/phase/imrlin/ImrLinearizer.java`
- Assembly instruction selection:
  `prev26/src/prev26lang/phase/asmgen/AsmGenerator.java`
- Prologue, epilogue, runtime routines, final register names:
  `prev26/src/prev26lang/phase/finasm/FinAsm.java`

## 1. Syntax: Recognizing Functions And Calls

Phase: `synan`

Main file: `Prev26Parser.g4`

Function definitions are parsed by rule `d`:

```text
fun name(params) : result_type = body
fun name(params) : result_type
```

The first form creates `AST.DefFunDefn`: a normal function with a body.

The second form creates `AST.ExtFunDefn`: an external function declaration. It
has a name, parameters, and result type, but no body. Runtime functions such as
`putint`, `getint`, `putchar`, and similar declarations use this shape.

Parameters are parsed into `AST.ParDefn` nodes. A parameter stores its name and
declared type.

Function calls are parsed as postfix expressions. If an expression is followed
by parentheses, the parser builds:

```text
AST.CallExpr(funExpr, argExprs)
```

This means the callee is itself an expression. So both of these fit the same AST
shape:

```prev26
f(1)
choose_inc()(1)
```

The first has `funExpr = NameExpr("f")`. The second has another call expression
as the callee expression.

## 2. Abstract Syntax: The Compiler's Function Shape

Phase: `abstr`

Main file: `AST.java`

Important nodes:

```text
AST.FunDefn
  name
  pars
  type

AST.DefFunDefn extends FunDefn
  expr        body expression

AST.ExtFunDefn extends FunDefn
  no body

AST.ParDefn
  name
  type

AST.CallExpr
  funExpr
  argExprs

AST.FunType
  parTypes
  resType
```

At this level, functions still look like high-level language constructs. There
is no stack frame yet, no static link yet, and no assembly yet.

Change this area when a function-like syntactic feature needs a new AST shape.
For example: named return values, default parameters, multiple return values, or
a different representation for closures.

## 3. Names: Connecting Calls To Definitions

Phase: `seman`, name resolution part

Main file: `NameResolver.java`

The name resolver connects each `NameExpr` to the definition it refers to. For
function calls, this is what lets:

```prev26
f(6)
```

become "call the function definition named `f`", not just "call some text named
`f`".

Function parameters create a new nested scope:

```text
outside function:
  function name is visible in the surrounding definition block

inside function:
  parameters are declared
  body expressions can refer to parameters
  body expressions can refer to visible outer names
```

For defined functions, parameter types and result type are resolved outside the
function body scope. Then the body is resolved inside a new scope containing the
parameters.

For external functions, parameters are still checked in a function scope so
duplicate parameter names are caught, but there is no body to resolve.

Change this area when an exercise changes lexical scoping, duplicate-name rules,
visibility of nested functions, or how parameter names are introduced.

## 4. Types: What A Function Is Allowed To Be

Phase: `seman`, type construction and type checking

Main files: `TypeConstructor.java`, `TypeChecker.java`, `TYP.java`

The compiler turns a function declaration into a semantic function type:

```text
TYP.FunType(parTypes, resType)
```

For:

```prev26
fun f(x : int, y : char) : bool = ...
```

the type is conceptually:

```text
(int, char) -> bool
```

Calls are checked like this:

```text
1. Type-check the callee expression.
2. Require the callee expression to have a function type.
3. Check that the argument count matches the parameter count.
4. Check that each argument type matches the corresponding parameter type.
5. The call expression gets the function's result type.
```

Defined functions also check that the body expression has the same type as the
declared result type.

This compiler also requires:

- `main` must be a defined function.
- `main` must have no parameters.
- `main` must return `int`.
- Parameter types must be simple or callable: `int`, `char`, `bool`, pointer, or
  function type.
- Function result types may also be `void`.

Change this area when an exercise changes valid parameter types, function result
rules, function type equivalence, argument compatibility, or what `main` must
look like.

## 5. Memory Layout: Where Parameters And Frames Live

Phase: `memory`

Main file: `Layouter.java`

This is where functions first become run-time storage objects.

For every defined function, the compiler creates a `MEM.Frame` containing:

```text
label      function entry label
depth      static nesting depth
locsSize   total local-variable area
argsSize   maximum outgoing call area needed by this function
size       base frame size before saved physical registers
FP         fixed frame-pointer temporary
RV         temporary holding this function's return value
```

External functions do not get frames, because their bodies are not compiled by
this compiler. Their parameters are still given accesses so they can be logged
and reasoned about consistently.

### Static Depth

Global functions have depth `0`.

Nested functions have depth one greater than the function they are inside:

```text
main              depth 0
  makeSum         depth 1
    addWithLinks  depth 2
      deep        depth 3
```

This depth is what makes static links possible later.

### Parameter Layout

Every call record reserves offset `0` for the static link. User parameters start
after it:

```text
FP + 0   static link on entry, return value on exit
FP + 8   first parameter
FP + 16  second parameter
FP + 24  third parameter
...
```

In `Layouter.java`, this is controlled by:

```text
STATIC_LINK_SIZE = 8
FIRST_PARAM_OFFSET = STATIC_LINK_SIZE
```

So `layoutParameter` stores each `AST.ParDefn` as a `MEM.RelAccess` with a
positive offset and the function's static depth.

The key stack picture after the callee prologue is:

```text
higher addresses

caller frame ...

callee FP + 16   second parameter
callee FP + 8    first parameter
callee FP + 0    static link on entry, return value on exit
                 ^
                 this is also the caller's SP during the call

callee FP - 8    local / temporary frame area starts below FP
callee FP - 16
...

callee SP        bottom of callee frame, including outgoing call area

lower addresses
```

So when you see a positive offset from `FP`, think "incoming call record". When
you see a negative offset from `FP`, think "this function's own frame".

### Local Variable Layout

Local variables are stored at negative offsets from `FP`:

```text
FP - 8
FP - 16
FP - 24
...
```

The access also remembers the depth of the function that owns the variable. That
depth matters when an inner function accesses an outer variable.

### Outgoing Argument Area

Each function frame reserves enough space for the largest call made inside that
function.

For each call, `callAreaSize` reserves:

```text
8 bytes for the static link
one aligned slot for each argument
```

The maximum of all calls in the function becomes `frame.argsSize`.

Change this area when an exercise changes parameter offsets, frame shape, static
link placement, alignment, argument slot sizes, local-variable layout, or how
much outgoing call space each function needs.

## 6. Static Links: How Nested Functions See Outer Variables

Phases: `memory`, `imrgen`, `finasm`

Main file for the logic: `ImrGenerator.java`

A static link points from a called function to the frame of its lexical parent.
It is different from a return address:

```text
return address  says where execution continues after the call
static link     says where the callee's outer variables live
```

When an inner function uses a variable from an outer function, the compiler
walks the static-link chain until it reaches the frame where the variable was
defined.

For example:

```prev26
fun main() : int =
    let
        var base : int
        fun inner(x : int) : int =
            base + x
    in
        inner(3)
    end
```

`base` belongs to `main`, but `inner` uses it. Inside `inner`, the compiler must
not look for `base` in `inner`'s own locals. It follows the static link from
`inner`'s frame to `main`'s frame, then uses `base`'s offset inside that frame.

In `ImrGenerator.java`:

- `staticLinkAt(framePointer)` means "load the static link stored at this frame".
- `staticChainTo(targetDepth)` starts at the current `FP` and follows static
  links until it reaches the requested lexical depth.
- `addressOfAccess` uses that frame pointer plus the variable's offset.
- `callStaticLink(callee)` computes which frame pointer should be passed to the
  callee at call-record offset `0`.

For a direct call:

```text
callee is global        -> static link slot gets a dummy value
callee is nested        -> static link slot gets callee's parent frame pointer
```

For an indirect call through a function value, this compiler currently treats
function values as code pointers only. It does not store a closure environment
with them, so `indirectCallStaticLink` passes a dummy static link. Direct calls
are where the lexical static link is preserved.

If an exercise asks for proper closures or nested function values that retain
their environment, this is one of the central places you would need to change:
function values would need to carry both code address and environment pointer,
and indirect calls would need to pass that environment as the static link.

## 7. Intermediate Representation: Function Bodies And Calls

Phase: `imrgen`

Main file: `ImrGenerator.java`

Each defined function body becomes an IMR statement tree:

```text
LABEL(bodyEntry)
MOVE(TEMP(frame.RV), bodyExpression)
JUMP(bodyExit)
LABEL(bodyExit)
```

This means the body computes a value into the function's return-value temporary
`frame.RV`. Later, final assembly stores that value into the return slot.

Each function call becomes:

```text
IMR.CALL(addr, offsets, args)
```

where:

```text
addr     expression producing the callee code address
offsets  stack offsets for static link and arguments
args     static link expression followed by user argument expressions
```

For a call with two user arguments:

```text
offsets = [0, 8, 16]
args    = [staticLink, arg0, arg1]
```

The first argument in `args` is not a source-level argument. It is the hidden
static link.

Change this area when a function call's behavior is wrong even though names,
types, and frame layout are correct. This is usually the place for exercises
about static links, direct vs indirect calls, return-value handling, nested
function access, or lowering new function-like constructs.

## 8. Linearization: Making Calls Executable In Order

Phase: `imrlin`

Main file: `ImrLinearizer.java`

The intermediate representation can be nested:

```text
f(g(1), h(2))
```

contains calls inside arguments to another call. The linearizer makes evaluation
order explicit.

For calls, `canonCall`:

```text
1. canonicalizes the call address
2. evaluates each argument from left to right
3. stores each evaluated value into a temporary
4. rebuilds a canonical IMR.CALL using those temporaries
```

This matters because assembly generation expects calls and memory operations to
already be in a manageable order.

Change this area when the right operations are generated but nested calls,
side-effect order, or "call inside expression" behavior is wrong.

## 9. Assembly Generation: Writing The Call Record And Jumping

Phase: `asmgen`

Main file: `AsmGenerator.java`

`munchCall` translates `IMR.CALL`.

It does three main things:

```text
1. Evaluate each hidden/user argument expression.
2. Store each value into the outgoing call area at SP + offset.
3. Jump to the callee with jal or jalr.
```

Direct calls use `jal`:

```text
jal RA, function_label
```

Indirect calls use `jalr`:

```text
jalr RA, address_temp, 0
```

The return address temporary `MEM.RA` is fixed to real register `x1` later.

After the call, if the call's result is needed, assembly generation loads it
from:

```text
0(SP)
```

That is the caller's return-value slot.

Change this area when arguments are stored in the wrong order or wrong offsets,
direct/indirect calls use the wrong instruction, return values are loaded from
the wrong place, or call instructions have wrong input/output metadata.

## 10. Final Assembly: Prologue, Epilogue, Return Value

Phase: `finasm`

Main file: `FinAsm.java`

Final assembly wraps each compiled function body with a prologue and epilogue.

On function entry, before the prologue:

```text
SP points to the caller's outgoing call area
SP + 0 has the static link
SP + 8 has the first source-level argument
```

The prologue:

```text
1. subtracts the callee frame size from SP
2. saves allocated physical registers
3. saves old FP
4. saves return address
5. sets FP = SP + frameSize
6. jumps to the body entry label
```

After step 5:

```text
callee FP == caller SP from before the prologue
```

So the callee can read its incoming call record using positive `FP` offsets.

The epilogue:

```text
1. stores frame.RV to 0(FP)
2. restores saved physical registers
3. restores return address
4. restores old FP
5. adds the frame size back to SP
6. returns with jalr x0, RA, 0
```

The store to `0(FP)` is the return-value handoff. Since `FP` equals the caller's
pre-call `SP`, the caller can read the result from `0(SP)` after the call.

Change this area when function entry/exit assembly is wrong, return values do
not land in the caller's expected slot, saved registers are misplaced, or stack
pointer/frame pointer restoration is wrong.

## 11. Runtime External Functions

Phase: `finasm`

External declarations such as:

```prev26
fun putint(i : int) : void
fun getint() : int
```

do not generate normal code chunks. Instead, `FinAsm.java` emits runtime labels
such as:

```text
_putint
_putchar
_getint
_getchar
_new
_del
_exit
```

These routines follow the same call-record convention from the caller's point of
view. For example, after the runtime helper saves syscall registers by moving
`SP`, it reads the first user argument at the adjusted address corresponding to
the original `SP + 8`, and it writes return values to the adjusted address
corresponding to the original `SP + 0`.

Change this area when an external function has the wrong runtime behavior, wrong
environment-call number, wrong argument offset, or wrong return slot.

## 12. Register Allocation And Calls

Phases: `livean`, `regall`

Main files: `FlowGraph.java`, `LiveAnalyzer.java`, `RegisterAllocator.java`,
`RegAll.java`

Calls are marked as `ASM.ControlFlow.CALL`. For control-flow analysis, calls
fall through to the next instruction after returning.

The fixed temporaries:

```text
MEM.SP -> x2
MEM.FP -> x8
MEM.RA -> x1
```

are not allocated like ordinary temporaries. Other temporaries, including
argument evaluation temporaries and return-value temporaries, are assigned
physical registers or spilled.

Change this area when the call code is structurally right but temporaries around
calls get incorrect registers, spill code breaks call behavior, or liveness
around `jal`/`jalr` is wrong.

## Debugging Checklist

When a function/call exercise fails, ask where the first wrong fact appears:

- Syntax wrong? Check `Prev26Parser.g4`.
- AST node shape wrong? Check `AST.java` and parser actions.
- Function name resolves to the wrong definition? Check `NameResolver.java`.
- Argument count or type mismatch wrong? Check `TypeChecker.java`.
- Parameter offsets, frame size, or outgoing call area wrong? Check
  `Layouter.java`.
- Static link or variable access through outer scopes wrong? Check
  `ImrGenerator.java`.
- Nested call evaluation order wrong? Check `ImrLinearizer.java`.
- Call instruction, argument stores, or return-value load wrong? Check
  `AsmGenerator.java`.
- Prologue, epilogue, final return slot, or runtime function wrong? Check
  `FinAsm.java`.
- Register or spill behavior around calls wrong? Check `livean` and `regall`.
