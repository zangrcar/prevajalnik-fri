# Prev26 Compiler Phases

This file is a mental map of the compiler pipeline. It explains what each phase
is responsible for, what kind of information it adds, and when you probably want
to change that phase during exercises.

It intentionally does not explain the implementation details. Think of it as a
navigation guide: "where in the compiler does this kind of problem belong?"

The phase folders follow the same names under
`prev26/src/prev26lang/phase/<phase-name>/`.

## Pipeline At A Glance

```text
.p26 source code
  -> lexan   tokens
  -> synan   concrete syntax tree
  -> abstr   abstract syntax tree
  -> seman   names, types, constants, addressability
  -> memory  frames, variable locations, data labels
  -> imrgen  tree-shaped intermediate representation
  -> imrlin  linear code/data chunks
  -> asmgen  assembly instructions with temporaries
  -> livean  live-in/live-out temporary sets
  -> regall  real registers or spills
  -> finasm  final assembly file
```

The early phases decide what the program means. The middle phases decide how
that meaning becomes a lower-level program. The late phases decide how that
lower-level program fits the target machine.

## How To Decide Where To Work

If the compiler does not even recognize characters, keywords, literals, comments,
or operators, look at `lexan`.

If the tokens are recognized but the program shape is rejected, look at `synan`.

If the syntax parses but the AST shape is wrong or inconvenient, look at
`abstr`.

If the question is "is this program allowed?" or "what does this name/type
refer to?", look at `seman`.

If the question is "where does this value live at run time?", look at `memory`.

If the question is "how do I express this language construct as basic operations?",
look at `imrgen`.

If the intermediate code exists but needs to be ordered into executable steps,
look at `imrlin`.

If the question is "which machine instructions should implement this operation?",
look at `asmgen`.

If registers are being chosen badly or temporaries interfere incorrectly, look at
`livean` and `regall`.

If the assembly is conceptually correct but the final `.asm` file, prologue,
epilogue, labels, directives, or formatting are wrong, look at `finasm`.

## `lexan` - Lexical Analysis

Purpose: turn raw source text into tokens.

This phase reads characters and groups them into meaningful units: identifiers,
keywords, constants, operators, punctuation, and end-of-file. It also decides
which text is ignored, such as whitespace or comments. Locations are attached so
later errors can point back to the source file.

Change this phase when an exercise adds or changes a keyword, literal form,
operator spelling, comment syntax, escape sequence, or any rule about how source
characters become tokens.

## `synan` - Syntax Analysis

Purpose: check whether the token stream has a valid grammatical structure.

This phase reads tokens and recognizes larger language forms: declarations,
types, expressions, statements, function definitions, parameter lists, blocks,
and operator precedence. Its job is mainly structural: "can these tokens form a
valid Prev26 program?"

Change this phase when an exercise changes the grammar: new statement forms, new
expression forms, different precedence, optional syntax, separators, delimiters,
or how declarations are written.

## `abstr` - Abstract Syntax

Purpose: turn parsed syntax into the AST shape used by the rest of the compiler.

The parser sees many concrete details that are useful only for recognizing the
program. The abstract syntax tree keeps the important meaning-bearing structure:
definitions, uses of names, expressions, types, and statement-like constructs.
This is the compiler's main high-level view of the program.

Change this phase when a syntactic feature needs a new AST node, when parse-tree
details must be normalized into a simpler form, or when later phases need a
clearer representation of some language construct.

## `seman` - Semantic Analysis

Purpose: decide whether the parsed program is meaningful.

This phase answers questions that grammar alone cannot answer. It connects name
uses to definitions, builds the internal view of types, checks type rules, checks
whether expressions are constant where needed, and marks which expressions can
act as assignable addresses.

Change this phase when an exercise changes scoping, name visibility,
overloading-like behavior, type rules, valid operands, function-call rules,
constant-expression rules, assignment rules, or the distinction between values
and assignable locations.

## `memory` - Memory Layout

Purpose: decide where run-time objects live.

This phase maps declarations to storage. Global variables and strings get fixed
labels. Parameters, local variables, and record components get relative
locations. Functions get stack-frame information such as static depth, local
space, outgoing argument space, return value storage, and frame size.

Change this phase when an exercise concerns sizes, alignment, stack frames,
static links, local variables, parameters, record fields, string constants,
global storage, or calling-convention storage layout.

## `imrgen` - Intermediate Representation Generation

Purpose: translate the AST meaning into machine-independent operations.

This phase lowers high-level language constructs into intermediate code. It
turns expressions, assignments, conditionals, loops, function calls, variable
accesses, array/record access, and returns into a smaller set of generic
operations such as arithmetic, memory loads/stores, labels, jumps, calls, and
temporaries.

Change this phase when the compiler understands a construct semantically but
does not yet produce the right run-time behavior for it. This is often the right
place for exercises about how `if`, `while`, assignments, calls, static links,
array indexing, record access, or short-circuit evaluation should execute.

## `imrlin` - Intermediate Representation Linearization

Purpose: turn tree-shaped intermediate code into ordered chunks.

The intermediate representation can still be nested, because expressions can
contain subexpressions and statements. This phase flattens that structure into
linear sequences of statements. It also separates the program into data chunks
and one code chunk per function, with entry and exit labels.

Change this phase when nested intermediate code is correct in idea but is not
being ordered, simplified, or chunked correctly before assembly generation.

## `asmgen` - Assembly Generation

Purpose: select target instructions while still using abstract temporaries.

This phase translates linear intermediate code into assembly-like instructions.
The instructions already look close to target assembly, but they still mention
temporary variables instead of final physical registers. Each instruction also
records which temporaries it reads, which it writes, and which labels it may
jump to.

Change this phase when an operation lowers to the wrong machine instruction
sequence, when loads/stores/calls/branches are wrong, or when instruction
metadata about inputs, outputs, moves, or jump targets is wrong.

## `livean` - Liveness Analysis

Purpose: find which temporaries are still needed at each program point.

This phase studies the control flow of generated assembly and computes live-in
and live-out sets for every instruction. A temporary is live if its current value
may still be read later before being overwritten. This information is the basis
for deciding which temporaries cannot share the same register.

Change this phase when register allocation fails because the compiler has the
wrong idea about which temporary values are alive across instructions, branches,
loops, calls, or returns.

## `regall` - Register Allocation

Purpose: replace abstract temporaries with real registers or spill locations.

This phase uses liveness information to decide which temporaries can share
registers and which ones conflict. If there are not enough registers, some
temporaries must be stored in memory and loaded back when needed. After this
phase, the program should no longer depend on unlimited temporary variables.

Change this phase when exercises concern interference graphs, graph coloring,
spill code, register limits, move handling, or the mapping from temporaries to
physical registers.

## `finasm` - Final Assembly Emission

Purpose: write the final assembly file.

This phase turns allocated code and data into the actual `.asm` text. It is
where final sections, directives, labels, prologues, epilogues, register names,
stack adjustment, saved registers, and file output come together.

Change this phase when the compiler has the right abstract instructions and
register decisions but the final generated assembly file is malformed, missing
required boilerplate, using wrong final register names, or arranging function
entry/exit code incorrectly.

## `all` And Target Phases

`all` is not a separate compiler idea. It means "run the whole pipeline".

When debugging, `--target-phase=<phase>` stops after a chosen phase. Use it to
ask: "does the program still look correct at this boundary?" The first phase
where the output looks wrong is usually near the place you should inspect.

Useful boundaries:

- Stop at `lexan` when tokenization is suspicious.
- Stop at `synan` or `abstr` when syntax shape is suspicious.
- Stop at `seman` when meaning, names, or types are suspicious.
- Stop at `memory` when storage layout is suspicious.
- Stop at `imrgen` or `imrlin` when run-time behavior is suspicious but still
  machine-independent.
- Stop at `asmgen` when instruction selection is suspicious.
- Stop at `livean` or `regall` when register behavior is suspicious.
- Stop at `finasm` when the final `.asm` text is suspicious.
