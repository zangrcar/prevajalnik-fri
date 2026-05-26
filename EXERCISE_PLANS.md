# Previous-Year Exercise Implementation Plans

## Table Of Contents

- [Exercise 3: Save Return Address With Saved Registers](#exercise-3-save-return-address-with-saved-registers)
- [Exercise 3: Return Result Through A Dedicated Memory Slot](#exercise-3-return-result-through-a-dedicated-memory-slot)
- [Exercise 3: Return Result Through A Local Register](#exercise-3-return-result-through-a-local-register)
- [Exercise 3: Add `dump()` To Print Active Call Records](#exercise-3-add-dump-to-print-active-call-records)
- [Exercise 2: Save FP And RA At The Bottom Of The Call Record Only When Needed](#exercise-2-save-fp-and-ra-at-the-bottom-of-the-call-record-only-when-needed)
- [Exercise 2: Short-Circuit Conditions With `and` And `or`](#exercise-2-short-circuit-conditions-with-and-and-or)
- [Exercise 1: Special Local Variable `result`](#exercise-1-special-local-variable-result)
- [Exercise 3: Caller Saves Registers Around Calls](#exercise-3-caller-saves-registers-around-calls)
- [Exercise 3: Force Addition And Subtraction Results Into One Register](#exercise-3-force-addition-and-subtraction-results-into-one-register)

## Exercise 3: Save Return Address With Saved Registers

### What It Demands

The exercise says to change the compiler so that the return address is no longer stored in the call record. Instead, it should be stored together with the saved registers, beside the "working" registers whose number is controlled by `--num-regs`.

In practical terms:

- The return address register `RA` / `x1` is currently treated as a special fixed register.
- The final assembly prologue currently stores `x1` in a dedicated frame slot.
- This exercise wants `x1` saved in the same save area as the physical registers used by register allocation.
- Because the dedicated return-address slot disappears, the call record/frame fixed part becomes smaller by 8 bytes.
- The exercise explicitly says the interpreter will not work anymore, so do not spend time adapting `imrlin.Interpreter` unless you want it for your own debugging.

The important distinction:

- Do not change the number of working registers controlled by `--num-regs`.
- Do change where the return address is saved in the final stack frame.

### Where It Fits In Prev26

The relevant files are:

- `prev26/src/prev26lang/phase/memory/Layouter.java`
- `prev26/src/prev26lang/phase/regall/RegisterAllocator.java`
- `prev26/src/prev26lang/phase/finasm/FinAsm.java`

Possibly useful for checking:

- `prev26/src/prev26lang/phase/memory/MEM.java`
- `prev26/src/prev26lang/phase/regall/RegAll.java`
- `prev26/src/prev26lang/phase/asmgen/AsmGenerator.java`

You should not need to change semantic analysis, IR generation, or ordinary assembly generation.

### Current Layout To Understand First

In the current compiler, a frame is shaped roughly like this, from low offsets relative to `SP` after function entry:

```text
0 ... argsSize-1           outgoing argument area
argsSize                   saved old FP
argsSize + 8               saved RA
argsSize + 16 ...          spill slots
...                        saved physical registers
...                        locals
```

This is why the code currently has patterns like:

```java
FIXED_FRAME_SIZE = 2 * ADDRESS_SIZE;
oldFramePointerOffset(frame) = frame.argsSize;
returnAddressOffset(frame) = frame.argsSize + 8;
registerSaveBase(frame) = frame.argsSize + 16 + spillSize(frame);
spillOffset(...) = baseFrame.argsSize + 16 + ...
```

The exercise wants to remove the dedicated `saved RA` slot and save `x1` with the saved physical registers instead.

After the change, the frame should be shaped like:

```text
0 ... argsSize-1           outgoing argument area
argsSize                   saved old FP
argsSize + 8 ...           spill slots
...                        saved physical registers
...                        saved RA
...                        locals
```

The exact order of "saved physical registers" and "saved RA" is not important as long as prologue and epilogue agree. The simplest implementation is to append `"x1"` to the saved-register list in `FinAsm`.

### Minimal Implementation Plan

1. In `Layouter.java`, reduce the fixed frame size.

Find:

```java
private static final long FIXED_FRAME_SIZE = 2*ADDRESS_SIZE;
```

Change it to:

```java
private static final long FIXED_FRAME_SIZE = ADDRESS_SIZE;
```

This removes the dedicated return-address slot from the abstract frame size. The remaining fixed slot is the old frame pointer.

2. In `RegisterAllocator.java`, move spill slots down by 8 bytes.

Find the spill offset calculation:

```java
offset = baseFrame.argsSize + 16 + 8L * nextSpillSlot;
```

Change it to:

```java
offset = baseFrame.argsSize + 8 + 8L * nextSpillSlot;
```

Also update the nearby comment from "old-FP/RA slots" to just "old-FP slot".

Reason: after removing the dedicated RA slot, spill slots start immediately after the old-FP slot.

3. In `FinAsm.java`, remove the dedicated return-address offset.

You can delete the helper:

```java
private long returnAddressOffset(final MEM.Frame frame) {
    return frame.argsSize + 8;
}
```

Then remove all use of `returnAddressOffset` in `emitPrologue` and `emitEpilogue`.

4. In `FinAsm.java`, update fixed-size calculations from `16` to `8`.

Change:

```java
final long spillSize = frame.size - frame.argsSize - 16 - frame.locsSize;
```

to:

```java
final long spillSize = frame.size - frame.argsSize - 8 - frame.locsSize;
```

Change:

```java
return frame.argsSize + 16 + spillSize(frame);
```

to:

```java
return frame.argsSize + 8 + spillSize(frame);
```

Keep:

```java
oldFramePointerOffset(frame) = frame.argsSize;
```

because the old FP still has its own fixed slot.

5. In `FinAsm.java`, save `x1` together with saved registers.

The simplest approach is to modify `savedRegisters` so it appends `"x1"`:

```java
private Vector<String> savedRegisters(final LinkedHashMap<MEM.Temp, String> registerMap) {
    final LinkedHashSet<String> saved = new LinkedHashSet<String>(registerMap.values());
    saved.add("x1");
    return new Vector<String>(saved);
}
```

This makes the existing prologue loop save `x1`, and the existing epilogue loop restore it.

The current code already does:

```java
for (int i = 0; i < savedRegisters.size(); i++)
    emitStore(savedRegisters.get(i), registerBase + 8L * i);
```

and later:

```java
for (int i = savedRegisters.size() - 1; i >= 0; i--)
    emitLoad(savedRegisters.get(i), registerBase + 8L * i);
```

So appending `"x1"` is enough.

6. In `FinAsm.java`, remove the old dedicated RA store/load.

In the prologue, remove:

```java
emitStore("x1", returnAddressOffset);
```

In the epilogue, remove:

```java
emitLoad("x1", returnAddressOffset);
```

`x1` is now handled by the saved-register loop.

### Why This Is Correct

The call instruction still writes the return address into `x1`:

```asm
jal x1, function
```

Nested calls may overwrite `x1`, so every function still needs to save its incoming return address. The only thing that changes is where it is saved:

- before: special RA slot in the fixed frame area
- after: regular saved-register area

Since `x1` is not part of `--num-regs`, adding it to the save area does not change the number of working registers.

### What Not To Change

Do not change `RegAll.ALLOCATABLE_REGISTERS`.

Do not add `RA` to the register allocator's ordinary register set.

Do not change `AsmGenerator.munchCall`; calls should still define `MEM.RA`.

Do not change semantic analysis or IR generation.

Do not try to preserve `imrlin.Interpreter` unless you are doing extra work beyond the exercise. The exercise says the interpreter will not work.

### Pitfalls

If you change `Layouter.FIXED_FRAME_SIZE` but forget `RegisterAllocator.spillOffset`, spilled temporaries will overlap with the old-FP slot or saved registers.

If you change `Layouter.FIXED_FRAME_SIZE` but forget `FinAsm.spillSize`, final assembly frame calculations will be inconsistent.

If you remove the dedicated RA slot but forget to save `"x1"` in the saved-register area, every function that calls another function can return to the wrong address.

If you add `"x1"` to `RegAll.ALLOCATABLE_REGISTERS`, that is wrong: `x1` is still a special return-address register, not a working register.

### Small Test Program

Use a nested call, because this is where losing `RA` becomes visible:

```prev
fun putint(i : int) : void

fun f(x : int) : int = x + 1

fun g(x : int) : int =
    f(x) + 10

fun main() : int =
    putint(g(6)),
    0
```

Expected output:

```text
17
```

Also test with low register count to force spills:

```sh
make -C prev26 bin
java -cp prev26/bin:prev26/src:prev26/lib/antlr-4.13.2-complete.jar prev26lang.Compiler \
  --logged-phase=none \
  --target-phase=finasm \
  --num-regs=2 \
  --dst-file-name=/tmp/ra-save-test.asm \
  path/to/test.p26
```

Then assemble/run with RARS if your runtime setup supports it.

## Exercise 3: Return Result Through A Dedicated Memory Slot

### What It Demands

The exercise says:

> Without changing the interpreter, change the compiler so that a function does not return its result through the stack, but through a special memory location prepared for this purpose.

In the current compiler, a normal compiled call works roughly like this:

```text
caller stores arguments at SP + offsets
caller executes jal
callee stores result at caller's SP + 0
caller loads result from SP + 0
```

This exercise wants the final generated program to work more like:

```text
caller stores arguments as before
caller executes jal
callee stores result into a global/internal return slot
caller loads result from that return slot
```

The phrase "without changing the interpreter" is important. The simplest correct solution is to leave `imrlin.Interpreter` and the IR convention alone, and implement this only in the final assembly path.

### Where It Fits In Prev26

The relevant files are:

- `prev26/src/prev26lang/phase/asmgen/AsmGenerator.java`
- `prev26/src/prev26lang/phase/finasm/FinAsm.java`

You probably do not need to change:

- `prev26/src/prev26lang/phase/imrgen/ImrGenerator.java`
- `prev26/src/prev26lang/phase/imrlin/Interpreter.java`
- `prev26/src/prev26lang/phase/memory/Layouter.java`
- `prev26/src/prev26lang/phase/memory/MEM.java`

The reason is that the exercise explicitly says not to change the interpreter. So the IMR/interpreter world may still think results are returned at `SP + 0`; only final generated assembly changes.

### Current Code To Understand First

In `AsmGenerator.munchCall`, after a call, the result is currently loaded from the caller's stack frame:

```java
if (dst != null)
    emit("ld *d0, 0(*s0)", temps(dst), temps(MEM.SP), new Vector<MEM.Label>());
```

In `FinAsm.emitEpilogue`, the callee currently stores its result through the frame pointer:

```java
lines.add("  sd " + renderTemp(codeChunk.frame.RV, registerMap) + ", 0(x8)");
```

At that moment, `x8` is the callee's frame pointer, which points back to the caller's stack pointer before the call. So `0(x8)` is the caller's `SP + 0` return slot.

Runtime functions also return through the stack. For example, `_getint`, `_getchar`, and `_new` currently store `x10` back to the caller's return slot.

### Minimal Implementation Plan

1. Choose one internal global label for the result slot.

Use the same label name in `AsmGenerator` and `FinAsm`, for example:

```java
private static final MEM.Label RESULT_SLOT = new MEM.Label("__retval");
```

This becomes the assembly label `___retval`, because `MEM.Label(String)` prepends `_`.

Use a name that is unlikely to collide with user names. If you want to be extra careful, use a longer internal name such as:

```java
new MEM.Label("__prev26_result")
```

2. In `AsmGenerator.java`, add a data chunk for the result slot.

In `generate(...)`, after copying existing data chunks, add one 8-byte static object:

```java
this.dataChunks.add(new ASM.DataChunk(8));
this.dataChunks.add(new ASM.DataChunk(RESULT_SLOT, 8));
```

The alignment chunk is not strictly exciting, but it keeps the return slot safely aligned for `ld`/`sd`.

3. In `AsmGenerator.java`, change call-result loading.

Replace this in `munchCall`:

```java
if (dst != null)
    emit("ld *d0, 0(*s0)", temps(dst), temps(MEM.SP), new Vector<MEM.Label>());
```

with a load from the result slot:

```java
if (dst != null)
    loadResultSlot(dst);
```

Add a helper:

```java
private void loadResultSlot(final MEM.Temp dst) {
    final MEM.Temp addr = new MEM.Temp();
    loadLabel(addr, RESULT_SLOT);
    emit("ld *d0, 0(*s0)", temps(dst), temps(addr), new Vector<MEM.Label>());
}
```

This keeps the change small and reuses the existing `loadLabel` helper.

4. In `FinAsm.java`, add the same result-slot label.

Near the other constants:

```java
private static final MEM.Label RESULT_SLOT = new MEM.Label("__retval");
```

Make sure the string is exactly the same as in `AsmGenerator`.

5. In `FinAsm.java`, store function results into the result slot.

In `emitEpilogue`, replace:

```java
lines.add("  sd " + renderTemp(codeChunk.frame.RV, registerMap) + ", 0(x8)");
```

with:

```java
emitStoreResultSlot(renderTemp(codeChunk.frame.RV, registerMap));
```

Add helpers:

```java
private void emitResultSlotAddress() {
    lines.add("  lui " + SCRATCH + ", %hi(" + RESULT_SLOT.name + ")");
    lines.add("  addi " + SCRATCH + ", " + SCRATCH + ", %lo(" + RESULT_SLOT.name + ")");
}

private void emitStoreResultSlot(final String register) {
    emitResultSlotAddress();
    lines.add("  sd " + register + ", 0(" + SCRATCH + ")");
}
```

This uses `x3` / `SCRATCH`, which `FinAsm` already reserves for final-phase address calculations.

6. In `FinAsm.java`, update runtime functions that return values.

These runtime functions return a value:

- `_getint`
- `_getchar`
- `_new`

They currently store the result through the stack, for example:

```java
lines.add("  sd " + SYSCALL_ARG + ", 16(x2)");
```

Change those stores to:

```java
emitStoreResultSlot(SYSCALL_ARG);
```

Do not change `_putint` or `_putchar` for return values, because their result type is `void`. Their argument passing stays exactly as your current calling convention requires.

7. Optional: update bootstrap printing if you use it.

In `emitBootstrap`, there is commented-out code that used to print the program's return value from the stack:

```java
// lines.add("  ld " + SYSCALL_ARG + ", 0(x2)");
```

If you ever re-enable that debug printing, load from the result slot instead:

```java
emitResultSlotAddress();
lines.add("  ld " + SYSCALL_ARG + ", 0(" + SCRATCH + ")");
```

If the printing stays commented out, this does not matter.

### Why This Is Correct

Every compiled function still computes its result into its frame return-value temporary:

```java
frame.RV
```

Only the final place where that value is stored changes:

- before: caller's stack slot
- after: one global/internal result slot

Every compiled caller immediately loads the result after the call into a temporary. That means a single global result slot is enough for ordinary nested calls and recursion:

```text
call f
load ___retval into temp
continue computing
```

If another call happens later, it can overwrite the result slot because the previous result has already been copied into a temporary.

### What Not To Change

Do not change `imrlin.Interpreter`.

Do not change `ImrGenerator.callArgOffsets`.

Do not change `Layouter.callAreaSize`.

Do not remove the stack slot at `SP + 0` if your compiler also uses it for the static link. In this compiler, offset `0` is still needed for the static link even if the result no longer comes back through the stack.

Do not move function results into a normal register unless the exercise says to use registers. This exercise specifically says a prepared memory location.

### Pitfalls

If you change only the callee epilogue but not `AsmGenerator.munchCall`, callers will still load from `SP + 0` and get the wrong value.

If you change only `AsmGenerator.munchCall` but not the epilogue, callers will load from the result slot before anyone stores there.

If you forget `_getint`, `_getchar`, or `_new`, normal user functions may work, but external functions that return values will fail.

If you try to change `imrlin.Interpreter`, you are doing more than the exercise asks. Worse, you can accidentally hide bugs in final assembly generation.

If your result-slot label collides with a user global label, assembly can break. Use a clearly internal name.

### Small Test Program

Use a program where one function calls another, because this checks that return values survive nested calls:

```prev
fun putint(i : int) : void

fun f(x : int) : int = x + 1

fun g(x : int) : int =
    f(x) + 10

fun main() : int =
    putint(g(6)),
    0
```

Expected output:

```text
17
```

Also test an external returning function if your runtime supports it:

```prev
fun new(size : int) : ^int

fun main() : int =
    let
        var p : ^int
    in
        p = new(sizeof int),
        p^ = 42,
        p^
    end
```

Expected program result:

```text
42
```

Recommended commands:

```sh
make -C prev26 bin
java -cp prev26/bin:prev26/src:prev26/lib/antlr-4.13.2-complete.jar prev26lang.Compiler \
  --logged-phase=none \
  --target-phase=finasm \
  --num-regs=2 \
  --dst-file-name=/tmp/result-slot-test.asm \
  path/to/test.p26
```

Then run the assembly with RARS.

## Exercise 3: Return Result Through A Local Register

### How This Differs From The Previous Result-Slot Exercise

This is similar to the previous exercise only in the high-level goal: the function result should no longer be returned through the stack.

The difference is the destination:

- previous exercise: result goes into one special memory location
- this exercise: result goes into one register

So do not extend the previous memory-slot solution. Instead, replace the stack return slot with a fixed return-value register in final assembly.

### What It Demands

The exercise says to change the compiler so that a function does not return its result through the stack, but through one of the local registers.

Currently the call/result convention is:

```text
callee stores result at caller's SP + 0
caller loads result from SP + 0
```

After the change:

```text
callee puts result into a fixed return register
caller copies that register into the destination temporary
```

The register must be fixed by convention. The caller and callee cannot independently choose a register, because they are allocated separately.

### Where It Fits In Prev26

The relevant files are:

- `prev26/src/prev26lang/phase/memory/MEM.java`
- `prev26/src/prev26lang/phase/regall/RegAll.java`
- `prev26/src/prev26lang/phase/asmgen/AsmGenerator.java`
- `prev26/src/prev26lang/phase/finasm/FinAsm.java`

You should not need to change:

- `NameResolver`
- `TypeConstructor`
- `TypeChecker`
- `Layouter`
- `ImrGenerator`

### Minimal Implementation Plan

1. Add a fixed temporary for the return-value register in `MEM.java`.

Add beside `FP`, `SP`, and `RA`:

```java
public static final Temp RVREG = new Temp("RVREG");
```

This is not the same as `frame.RV`. Existing `frame.RV` is the temporary holding the computed result inside one function. `MEM.RVREG` is the fixed calling-convention register used to pass that result back to the caller.

2. Map `RVREG` to one physical register in `RegAll.java`.

For example, use `x10`.

Change `isFixedRegister`:

```java
return temp.equals(MEM.SP)
    || temp.equals(MEM.FP)
    || temp.equals(MEM.RA)
    || temp.equals(MEM.RVREG);
```

Change `fixedRegisterName`:

```java
if (temp.equals(MEM.RVREG))
    return "x10";
```

If `x10` is currently in `ALLOCATABLE_REGISTERS`, remove it. To keep the number of working registers the same, replace it with an unused physical register such as `x4`, if your compiler does not already use it specially.

This is important: if `x10` remains allocatable, the caller may keep a live temporary in `x10` across a call, and the callee's return value would overwrite it.

3. In `AsmGenerator.munchCall`, load the result from the return register instead of the stack.

Replace:

```java
if (dst != null)
    emit("ld *d0, 0(*s0)", temps(dst), temps(MEM.SP), new Vector<MEM.Label>());
```

with:

```java
if (dst != null)
    emit(ASM.move("addi *d0, *s0, 0", dst, MEM.RVREG));
```

This means:

```text
after call: dst = RVREG
```

4. In `FinAsm.emitEpilogue`, put the function result into the fixed return register.

Currently:

```java
lines.add("  sd " + renderTemp(codeChunk.frame.RV, registerMap) + ", 0(x8)");
```

Change this to a register move:

```java
lines.add("  addi " + RegAll.fixedRegisterName(MEM.RVREG) + ", "
    + renderTemp(codeChunk.frame.RV, registerMap) + ", 0");
```

Place this late enough that it is not overwritten by register restores. A simple safe shape is:

```java
for (int i = savedRegisters.size() - 1; i >= 0; i--)
    emitLoad(savedRegisters.get(i), registerBase + 8L * i);

lines.add("  addi " + RegAll.fixedRegisterName(MEM.RVREG) + ", "
    + renderTemp(codeChunk.frame.RV, registerMap) + ", 0");

emitLoad("x1", returnAddressOffset);
emitLoad("x8", oldFpOffset);
emitAddToSP(frameSize);
lines.add("  jalr x0, x1, 0");
```

This assumes `RVREG` is not among `savedRegisters`, which is true if it is a fixed register and not allocatable.

5. In `FinAsm.emitRuntime`, update runtime functions that return values.

Runtime functions that return values:

- `_getint`
- `_getchar`
- `_new`

They already get their Ripes result in `x10` / `SYSCALL_ARG`. If `RVREG` is also `x10`, remove the old stack stores:

```java
lines.add("  sd " + SYSCALL_ARG + ", 16(x2)");
```

The value is already in the return register.

If you choose a different `RVREG`, then add a move from `SYSCALL_ARG` to that register instead.

6. Optional: update bootstrap printing if enabled.

If the bootstrap ever prints the program result, it should read the return register after calling `_main`, not load from `0(x2)`.

### Why This Is Correct

The caller and callee now share a fixed convention:

```text
callee returns result in RVREG
caller copies RVREG into its destination temporary
```

Because `RVREG` is a fixed register and not allocatable, no normal temporary should be placed there. That prevents accidental clobbering of live caller values.

This mirrors normal machine conventions such as returning values in `a0`, except here we explicitly wire it into your compiler's fixed-register model.

### What Not To Change

Do not let the callee and caller choose return registers independently.

Do not keep the chosen physical return register in `ALLOCATABLE_REGISTERS`.

Do not change `Layouter.callAreaSize`; the stack area for arguments is a separate issue.

Do not change `ImrGenerator.callArgOffsets`; offset `0` can still be used for the static link even though the result no longer returns there.

### Pitfalls

If the return register is still allocatable, programs can fail only when a caller has a live value in that physical register across a call. Those bugs are painful because simple tests may still pass.

If you move `frame.RV` into the return register before restoring saved registers, and the return register is restored afterwards, your result can be overwritten.

If you change only the callee epilogue, callers will still load old stack garbage from `SP + 0`.

If you change only the caller, it will copy an uninitialized or stale return register.

If you forget `_getint`, `_getchar`, or `_new`, user-defined functions may work but external returning functions will fail.

### Small Test Program

Use nested calls:

```prev
fun putint(i : int) : void

fun f(x : int) : int = x + 1

fun g(x : int) : int =
    f(x) + 10

fun main() : int =
    putint(g(6)),
    0
```

Expected output:

```text
17
```

Also test with low register count:

```sh
make -C prev26 bin
java -cp prev26/bin:prev26/src:prev26/lib/antlr-4.13.2-complete.jar prev26lang.Compiler \
  --logged-phase=none \
  --target-phase=finasm \
  --num-regs=2 \
  --dst-file-name=/tmp/result-register-test.asm \
  path/to/test.p26
```

Then run with RARS.

## Exercise 3: Add `dump()` To Print Active Call Records

### What It Demands

The exercise says to add a standard-library function:

```prev
fun dump() : void
```

When called, `dump` must print the call records currently on the stack, starting with the active one and ending with `main`.

For each call record it must print the lowest address of that record. In this compiler, that is the value of `SP` while that function is active.

The important restriction is:

- do not assume a maximum number of call records
- do not solve it only in the interpreter

So `dump` needs to follow a real chain through the final stack frames until it reaches `main`.

### Where It Fits In Prev26

The relevant files are:

- `prev26/src/prev26lang/phase/memory/Layouter.java`
- `prev26/src/prev26lang/phase/finasm/FinAsm.java`

Possibly useful for checking:

- `prev26/src/prev26lang/phase/imrgen/ImrGenerator.java`
- `prev26/src/prev26lang/phase/asmgen/AsmGenerator.java`
- `prev26/src/prev26lang/phase/regall/RegAll.java`

You probably do not need to change the parser or semantic analysis if your programs already declare external functions like this:

```prev
fun dump() : void
```

The compiler will generate a call to `_dump`, and `FinAsm` can provide that runtime label just like `_putint`, `_getint`, and `_new`.

### Current Layout To Understand First

Right now, a frame is roughly:

```text
SP + 0 ... argsSize-1       outgoing argument area
SP + argsSize               saved old FP
SP + argsSize + 8           saved RA
...                         spills, saved registers, locals
FP                           top of frame / old SP before prologue
```

The saved old `FP` is not at a fixed offset from `SP`, because `argsSize` can be different for every function.

That means a generic `_dump` routine cannot simply do:

```asm
ld next, SOME_OFFSET(sp)
```

because `SOME_OFFSET` is not the same for all frames.

Also do not use `SP + 0` as the new dump link in this compiler. Offset `0` is already used by the outgoing static link and by the current stack-return convention, so overwriting it would break calls/results.

### Simple Frame-Chain Idea

Reserve one hidden word at a fixed offset from `FP`:

```text
FP - 8                      caller's FP, only for dump
```

Then `dump` can walk frames using two values:

```text
current SP = lowest address of current frame, print this
current FP = top of current frame
caller SP  = current FP
caller FP  = memory[current FP - 8]
```

So the loop is:

```text
print currentSP
callerFP = *(currentFP - 8)
if callerFP == 0: stop
currentSP = currentFP
currentFP = callerFP
repeat
```

For `main`, store `0` in this hidden slot. That gives the loop a clean stopping condition after printing `main`'s frame.

### Minimal Implementation Plan

1. In `Layouter.java`, reserve one hidden local slot in every defined function frame.

Add a constant near the other frame constants:

```java
private static final long DUMP_LINK_SIZE = ADDRESS_SIZE;
```

In the `FunCtx` constructor, initialize defined functions with one reserved word below `FP`:

```java
this.nextLocalOffset = hasFrame ? -DUMP_LINK_SIZE : 0;
this.locsSize = hasFrame ? DUMP_LINK_SIZE : 0;
```

instead of:

```java
this.nextLocalOffset = 0;
this.locsSize = 0;
```

Do not change parameter offsets. This reserved word lives at the top of the current frame, not in the outgoing argument area.

2. In `FinAsm.java`, store the fixed dump link in every prologue.

Add a helper:

```java
private boolean isMainFrame(final MEM.Frame frame) {
    return frame.label.name.equals("_main");
}
```

In `emitPrologue`, after `SP` has been decreased but before `emitSetFP(frameSize)`, store the previous `FP` into the hidden slot:

```java
emitStore(isMainFrame(codeChunk.frame) ? "x0" : "x8", frameSize - 8);
```

Why before `emitSetFP`?

- before `emitSetFP`, `x8` still contains the caller's `FP`
- after `emitSetFP`, `x8` becomes the current function's `FP`

`frameSize - 8` means:

```text
SP + frameSize - 8 == FP - 8
```

So this stores the caller's `FP` into the fixed dump-link slot.

3. In `FinAsm.java`, add the runtime function `_dump`.

Add a new block inside `emitRuntime()`:

```asm
_dump:
  addi x3, x2, 0        # x3 = current SP, the address to print
  addi x4, x8, 0        # x4 = current FP
  addi x2, x2, -16
  sd x10, 0(x2)
  sd x17, 8(x2)

_dump_loop:
  addi x10, x3, 0
  addi x17, x0, 34      # RARS PrintIntHex; use 1 for decimal PrintInt
  ecall
  addi x10, x0, 10
  addi x17, x0, 11      # PrintChar newline
  ecall

  ld x10, -8(x4)        # x10 = caller FP
  beq x10, x0, _dump_end
  addi x3, x4, 0        # next SP = current FP
  addi x4, x10, 0       # next FP = caller FP
  jal x0, _dump_loop

_dump_end:
  ld x17, 8(x2)
  ld x10, 0(x2)
  addi x2, x2, 16
  jalr x0, x1, 0
```

Notes:

- `x3` is already used as final-assembly scratch and is not allocatable in this compiler.
- `x4` is also not in `RegAll.ALLOCATABLE_REGISTERS`, so it is a convenient second runtime scratch.
- `x10` and `x17` are saved because they are used by RARS syscalls and are allocatable in this compiler.
- If your environment does not support syscall `34`, use syscall `1` instead. It prints the address as a decimal integer.

You can either write this directly as `lines.add(...)` calls, or create small helpers for printing an integer/address and newline.

4. Make sure `_dump` does not create its own call record.

Do not compile `dump` as an ordinary PREV function with a generated prologue. Add it as a final assembly runtime routine, like `_putint`.

That way, when `_dump` starts:

```text
x2 = caller's SP
x8 = caller's FP
```

which is exactly the active frame that should be printed first.

5. Use `dump` from PREV code as an external function.

Example:

```prev
fun dump() : void

fun f(x : int) : int =
    dump(),
    x + 1

fun main() : int =
    f(10)
```

This should print two frame addresses:

```text
address of f's frame
address of main's frame
```

The exact numbers do not matter. What matters is:

- one line per active compiled function
- first line is the function that called `dump`
- last line is `main`
- no fixed maximum depth

### Why This Is Correct

The frame chain is dynamic, not lexical.

That matters because the static link answers:

```text
where is my lexically enclosing function?
```

but `dump` needs:

```text
who called whom at runtime?
```

The hidden `FP - 8` slot stores the caller's `FP`, so it follows the runtime call chain. Since `caller SP == current FP` in this compiler's calling convention, `_dump` can also recover the caller's lowest frame address without knowing the caller's frame size.

### What Not To Change

Do not walk the static-link chain. That would be wrong for recursive calls and for calls between functions that are not direct lexical parent/child functions.

Do not assume a maximum number of frames and allocate a fixed array.

Do not put the dump link at `SP + 0` unless you also redesign the whole outgoing-argument/return-slot convention. In the current compiler, that offset is already meaningful.

Do not rely on `argsSize` inside `_dump`; it is different for different functions.

Do not solve only `imrlin.Interpreter`. The exercise explicitly wants the compiler/final program behavior.

### Pitfalls

If you forget to reserve the hidden local slot in `Layouter`, storing at `FP - 8` can overwrite a real local variable.

If you store the link after `emitSetFP(frameSize)`, then `x8` no longer contains the caller's `FP`. Store before changing `x8`, or load the saved old `FP` explicitly.

If `main` does not store `0` as the final link, `_dump` may continue into bootstrap/runtime stack data and print nonsense.

If `_dump` calls `emitRuntimeSave()` before copying the original `SP`, it will print the temporary syscall-save area instead of the caller's frame. Copy `x2` into a scratch register first.

If you use `x10` or `x17` without saving/restoring them, a caller that has live temporaries in those registers can break after `dump()`.

### Small Test Program

```prev
fun dump() : void
fun putint(i : int) : void

fun h(x : int) : int =
    dump(),
    x + 1

fun g(x : int) : int =
    h(x + 2)

fun main() : int =
    putint(g(5)),
    0
```

Expected behavior:

```text
<address of h frame>
<address of g frame>
<address of main frame>
8
```

The address values themselves can differ between runs/simulators. The order and number of lines are the important part.

Recommended command:

```sh
make -C prev26 bin
java -cp prev26/bin:prev26/src:prev26/lib/antlr-4.13.2-complete.jar prev26lang.Compiler \
  --logged-phase=none \
  --target-phase=finasm \
  --num-regs=2 \
  --dst-file-name=/tmp/dump-test.asm \
  path/to/test.p26
```

Then run `/tmp/dump-test.asm` with RARS.

## Exercise 2: Save FP And RA At The Bottom Of The Call Record Only When Needed

### What It Demands

The exercise says:

> Change the compiler so that a function saves the frame pointer and return address at the bottom of the call record. Each individual value should be saved only if it is really needed.

In Prev26 terms:

- "klicni kazalec" is the frame pointer, `FP` / `x8`
- "povratni naslov" is the return address, `RA` / `x1`
- the function prologue decides whether to save them
- the function epilogue restores only the values that were saved

The "only if needed" part is the important optimization:

- save `RA` only if the function contains a call, because any nested `jal` overwrites `x1`
- save old `FP` only if this function changes `x8` and the caller will need its old `FP` back

In this current compiler, every compiled function establishes `FP = SP + frameSize`, and generated code uses `FP` for relative accesses and for the current stack-return convention. So the simple correct rule is:

```text
save old FP for every generated function
save RA only for non-leaf functions
```

You can omit old `FP` only if you also implement a larger "frame-pointer omission" optimization. That is possible, but it is not the shortest solution.

### Where It Fits In Prev26

The relevant files are:

- `prev26/src/prev26lang/phase/memory/Layouter.java`
- `prev26/src/prev26lang/phase/finasm/FinAsm.java`

Possibly useful:

- `prev26/src/prev26lang/phase/asmgen/ASM.java`
- `prev26/src/prev26lang/phase/asmgen/AsmGenerator.java`
- `prev26/src/prev26lang/phase/regall/RegisterAllocator.java`

You should not need semantic analysis or IR generation for the simple version.

### Current Layout To Understand First

Currently the fixed saved values are placed after the outgoing-argument area:

```text
SP + 0 ... argsSize-1       outgoing argument area
SP + argsSize               saved old FP
SP + argsSize + 8           saved RA
...                         spills and saved registers
...                         locals
FP                           top/fixed end of the frame
```

That is why `FinAsm` currently has:

```java
oldFramePointerOffset(frame) = frame.argsSize;
returnAddressOffset(frame) = frame.argsSize + 8;
```

For this exercise, move the saved values to the fixed end of the call record, beside `FP`, so their position no longer depends on `argsSize`.

A simple target layout is:

```text
SP + 0 ...                  outgoing argument area, spills, saved registers
...                         locals
FP - 16                     saved old FP
FP - 8                      saved RA, only for non-leaf functions
FP                           top/fixed end of the frame
```

For leaf functions, omit the saved `RA` slot:

```text
FP - 8                      saved old FP
FP
```

This keeps the existing argument/static-link convention untouched. Do not move these slots to raw `SP + 0` unless you are also prepared to shift all outgoing argument offsets and incoming parameter offsets.

### Minimal Implementation Plan

1. Decide which functions need a saved `RA`.

In `Layouter.java`, extend `FunCtx` with:

```java
boolean hasCall;
```

Then update `registerCall`:

```java
ctx.hasCall = true;
ctx.maxArgsSize = Math.max(ctx.maxArgsSize, callAreaSize(callExpr));
```

This means:

```text
hasCall == true  => function is non-leaf => save RA
hasCall == false => function is leaf     => incoming RA remains in x1
```

2. Reserve space at the fixed end of the frame.

Because local variables use negative offsets from `FP`, reserve the saved-value area before allocating real locals.

For the simple version:

```text
always reserve 8 bytes for saved old FP
reserve another 8 bytes for saved RA only if hasCall
```

The annoying detail is that `hasCall` may only become known while visiting the body, after some locals have already been laid out. To keep the implementation simple, use a tiny pre-scan of the function body before laying out locals:

```java
private boolean containsCallOutsideNestedFunctions(AST.Expr expr) { ... }
```

Use it at the start of `visit(AST.DefFunDefn)`:

```java
final boolean savesRA = containsCallOutsideNestedFunctions(defFunDefn.expr);
final FunCtx ctx = enterFunction(defFunDefn, true, savesRA);
```

Then initialize the context like:

```java
this.savesFP = hasFrame;
this.savesRA = savesRA;
this.nextLocalOffset = -savedControlSize();
this.locsSize = savedControlSize();
```

where:

```java
private long savedControlSize() {
    long size = 0;
    if (savesFP) size += ADDRESS_SIZE;
    if (savesRA) size += ADDRESS_SIZE;
    return size;
}
```

This reserves the slots closest to `FP`. Real locals will start below them.

3. Make the same decision available in `FinAsm`.

The cleanest small solution is to recompute leaf/non-leaf from the final instruction list:

```java
private boolean savesRA(final ASM.CodeChunk codeChunk) {
    for (final ASM.Instruction instr : codeChunk.instructions())
        if (instr.controlFlow() == ASM.ControlFlow.CALL)
            return true;
    return false;
}
```

If `ASM.Instruction` does not expose `controlFlow()` publicly, add a small getter there.

For `FP`, keep:

```java
private boolean savesFP(final ASM.CodeChunk codeChunk) {
    return true;
}
```

This matches the current compiler design.

4. Compute offsets from the final frame size.

Do not use:

```java
frame.argsSize
frame.argsSize + 8
```

Instead, compute offsets from the top of the final frame:

```java
private long savedRaOffset(final long frameSize) {
    return frameSize - 8;
}

private long savedFpOffset(final long frameSize, final boolean savesRA) {
    return frameSize - (savesRA ? 16 : 8);
}
```

These values are still offsets from `SP`, because `FP = SP + frameSize`.

5. Update the prologue in `FinAsm.java`.

Current shape:

```java
emitSubFromSP(frameSize);
...
emitStore("x8", oldFpOffset);
emitStore("x1", returnAddressOffset);
emitSetFP(frameSize);
```

Change it to:

```java
emitSubFromSP(frameSize);

for (int i = 0; i < savedRegisters.size(); i++)
    emitStore(savedRegisters.get(i), registerBase + 8L * i);

if (savesFP)
    emitStore("x8", savedFpOffset(frameSize, savesRA));

if (savesRA)
    emitStore("x1", savedRaOffset(frameSize));

emitSetFP(frameSize);
```

Important: store old `FP` before `emitSetFP`, because after `emitSetFP`, `x8` no longer contains the caller's frame pointer.

6. Update the epilogue in `FinAsm.java`.

Current shape:

```java
emitLoad("x1", returnAddressOffset);
emitLoad("x8", oldFpOffset);
emitAddToSP(frameSize);
lines.add("  jalr x0, x1, 0");
```

Change it to:

```java
if (savesRA)
    emitLoad("x1", savedRaOffset(frameSize));

if (savesFP)
    emitLoad("x8", savedFpOffset(frameSize, savesRA));

emitAddToSP(frameSize);
lines.add("  jalr x0, x1, 0");
```

For leaf functions, `x1` was never overwritten, so returning through the original `x1` is correct.

7. Remove the old fixed-slot assumptions.

In `FinAsm.java`, remove or stop using:

```java
oldFramePointerOffset(frame)
returnAddressOffset(frame)
```

Also update:

```java
spillSize(frame)
registerSaveBase(frame)
```

so they no longer assume the old `argsSize + 16` fixed area for `FP` and `RA`.

If you reserved the new saved-value slots as part of `locsSize`, the lower part of the frame is now:

```text
outgoing args
spills
saved physical registers
locals and saved FP/RA area near FP
```

So the spill/saved-register base should skip only the outgoing argument area and the spill slots, not the old `FP/RA` slots.

### Why This Is Correct

`RA` only needs saving when the function calls another function:

```asm
jal x1, _some_function
```

That instruction overwrites `x1`. A leaf function does not execute such a call, so its incoming return address remains in `x1` until the final `jalr`.

`FP` must be restored if the function changes `x8`, because the caller expects its own frame pointer to still be valid after the call. In your current compiler, every generated function sets `x8` to its own frame pointer, so old `FP` is really needed.

Moving the saved values to `FP - 8` / `FP - 16` makes them part of the fixed end of the frame instead of placing them after the outgoing argument area, whose size differs by function.

### What Not To Change

Do not make `x1` an allocatable register.

Do not save `RA` for every function if the exercise grader checks the "only if needed" part.

Do not skip saving old `FP` unless your generated code also avoids changing `x8`.

Do not move saved `FP/RA` to `SP + 0` in this compiler without also changing call argument offsets, static-link offsets, and return-value stack slots.

Do not let `Layouter` and `FinAsm` disagree about whether a function has an `RA` slot. If layout reserves it but final assembly does not expect it, or the reverse, locals can overlap with saved control data.

### Pitfalls

If the function contains a call to an external function such as `putint`, that still counts as a call. Save `RA`.

If the function contains a call only inside a nested function definition, that should not make the outer function non-leaf. The nested function has its own frame.

If you compute the saved offsets from `frame.size` but final assembly later adds saved physical-register slots, the offsets will be wrong. Compute from the final `frameSize` used by `emitSetFP`.

If you reserve the slots after laying out locals, the first local can overlap with saved `FP` or saved `RA`.

### Small Test Program

Test both leaf and non-leaf functions:

```prev
fun putint(i : int) : void

fun leaf(x : int) : int =
    x + 1

fun nonleaf(x : int) : int =
    leaf(x) + 10

fun main() : int =
    putint(nonleaf(6)),
    0
```

Expected output:

```text
17
```

Check the generated assembly:

- `_leaf` should not store/load `x1`
- `_nonleaf` should store/load `x1`
- generated functions should still restore `x8`

Recommended command:

```sh
make -C prev26 bin
java -cp prev26/bin:prev26/src:prev26/lib/antlr-4.13.2-complete.jar prev26lang.Compiler \
  --logged-phase=none \
  --target-phase=finasm \
  --num-regs=2 \
  --dst-file-name=/tmp/bottom-fp-ra-test.asm \
  path/to/test.p26
```

Then inspect and run `/tmp/bottom-fp-ra-test.asm` with RARS.

## Exercise 1: Special Local Variable `result`

### What It Demands

The exercise introduces special rules for a variable named `result`:

- `result` may be defined only as a local variable
- `result` must have the same type as the result type of the function where it is defined
- reading from `result` is not allowed
- writing to `result` ends the current function immediately
- the function result becomes the value written into `result`

So this:

```prev
fun f(x : int) : int =
    let
        var result : int
    in
        result = x + 1,
        x + 100
    end
```

should behave like:

```prev
fun f(x : int) : int =
    x + 1
```

because the assignment to `result` exits the function before `x + 100`.

### Where It Fits In Prev26

The relevant files are:

- `prev26/src/prev26lang/phase/seman/NameResolver.java`
- `prev26/src/prev26lang/phase/seman/TypeChecker.java`
- `prev26/src/prev26lang/phase/imrgen/ImrGenerator.java`

Possibly useful:

- `prev26/src/prev26lang/phase/seman/TypeConstructor.java`
- `prev26/src/prev26lang/phase/memory/Layouter.java`

You probably do not need to change final assembly.

### Current Behavior To Understand First

Currently `result` is just an ordinary variable name.

An assignment like:

```prev
result = expr
```

is translated by `ImrGenerator.visit(AST.AsgnExpr)` into a normal memory store:

```java
MOVE(MEM(addressOf(result)), expr)
```

For this exercise, assignment to the special local `result` must instead become:

```text
frame.RV = expr
jump to current function's exit label
```

That makes the function return immediately.

### Minimal Implementation Plan

1. Track the currently checked function.

In `TypeChecker.java`, add a field:

```java
private AST.DefFunDefn currentFunction = null;
```

In `visit(AST.DefFunDefn)`, set it while checking the body:

```java
final AST.DefFunDefn oldFunction = currentFunction;
currentFunction = defFunDefn;
try {
    ...
    defFunDefn.expr.accept(this, arg);
    ...
} finally {
    currentFunction = oldFunction;
}
```

Do the same kind of tracking in `NameResolver.java` only if you want to catch the "local only" rule there. Otherwise you can keep that rule in `TypeChecker`.

2. Check that `result` is only a local variable.

In `TypeChecker.visit(AST.VarDefn)`, after computing the variable type, add:

```java
if (varDefn.name.equals("result")) {
    if (currentFunction == null)
        throw new Report.Error(varDefn, "'result' may only be a local variable.");
}
```

This rejects global `result`.

It also rejects `result` in places not inside a function. In your AST, parameters are `ParDefn`, not `VarDefn`, so this rule does not accidentally allow parameters named `result`. If you want to forbid parameter `result` explicitly, also check `visit(AST.ParDefn)`.

3. Check that `result` has the function result type.

Still in `TypeChecker.visit(AST.VarDefn)`:

```java
if (varDefn.name.equals("result")) {
    final TYP.FunType funType = declaredFunType(currentFunction);
    requireEquiv(varDefn, requireType(varDefn.type), funType.resType);
}
```

If the function returns `int`, local `var result : int` is legal.

If the function returns `char`, local `var result : int` is illegal.

For a `void` function, `var result : void` should already be rejected by ordinary variable-type rules, so this effectively means `result` is not useful in `void` functions.

4. Add a helper for detecting the special variable use.

In `TypeChecker.java`, add:

```java
private boolean isResultName(final AST.Expr expr) {
    if (!(expr instanceof AST.NameExpr nameExpr))
        return false;

    final AST.Defn defn = SemAn.defAtAttr.get(nameExpr);
    return defn instanceof AST.VarDefn varDefn && varDefn.name.equals("result");
}
```

Use the same helper idea later in `ImrGenerator.java`.

This deliberately accepts only direct assignment:

```prev
result = expr
```

and not:

```prev
(result) = expr
result[0] = expr
result.i = expr
```

That is the simplest interpretation of "writing to variable `result`".

5. Reject reads from `result`.

In `TypeChecker.visit(AST.NameExpr)`, add:

```java
if (isResultName(nameExpr))
    throw new Report.Error(nameExpr, "Reading from 'result' is not allowed.");
```

But there is one exception: the left-hand side of assignment to `result` must be allowed, otherwise `result = expr` would be rejected before `visit(AST.AsgnExpr)` can handle it.

Use a small flag:

```java
private boolean allowingResultWriteTarget = false;
```

In `visit(AST.NameExpr)`:

```java
if (isResultName(nameExpr) && !allowingResultWriteTarget)
    throw new Report.Error(nameExpr, "Reading from 'result' is not allowed.");
```

In `visit(AST.AsgnExpr)`, visit the left side like this:

```java
final boolean writesResult = isResultName(asgnExpr.fstExpr);

if (writesResult) {
    final boolean old = allowingResultWriteTarget;
    allowingResultWriteTarget = true;
    try {
        asgnExpr.fstExpr.accept(this, arg);
    } finally {
        allowingResultWriteTarget = old;
    }
} else {
    asgnExpr.fstExpr.accept(this, arg);
}

asgnExpr.sndExpr.accept(this, arg);
```

Then continue with the existing assignment checks.

6. Generate early return in `ImrGenerator.java`.

Add fields:

```java
private MEM.Frame currentFrame = null;
private MEM.Label currentExitLabel = null;
```

You already have `currentFrame`; add `currentExitLabel`.

In `visit(AST.DefFunDefn)`, set it together with `currentFrame`:

```java
final MEM.Label oldExitLabel = currentExitLabel;
currentExitLabel = exitLabel;
try {
    ...
} finally {
    currentExitLabel = oldExitLabel;
}
```

7. Add the same result-name helper in `ImrGenerator.java`.

```java
private boolean isResultName(final AST.Expr expr) {
    if (!(expr instanceof AST.NameExpr nameExpr))
        return false;

    final AST.Defn defn = SemAn.defAtAttr.get(nameExpr);
    return defn instanceof AST.VarDefn varDefn && varDefn.name.equals("result");
}
```

8. Change assignment generation for `result`.

In `ImrGenerator.visit(AST.AsgnExpr)`, after visiting both subexpressions, special-case:

```java
if (isResultName(asgnExpr.fstExpr)) {
    final Vector<IMR.Stmt> body = stmts();
    body.add(new IMR.MOVE(new IMR.TEMP(currentFrame.RV), requireExprIR(asgnExpr.sndExpr)));
    body.add(new IMR.JUMP(new IMR.NAME(currentExitLabel)));
    putExprIR(asgnExpr, sexpr(body, new IMR.CONST(0)));
    return null;
}
```

Keep the ordinary assignment code for all other assignments.

This makes:

```prev
result = expr
```

compile to:

```text
RV = expr
jump function_exit
```

9. Keep the normal final expression rule.

Do not remove this existing part in `visit(AST.DefFunDefn)`:

```java
body.add(new IMR.MOVE(new IMR.TEMP(frame.RV), requireExprIR(defFunDefn.expr)));
body.add(new IMR.JUMP(new IMR.NAME(exitLabel)));
```

If a function never writes to `result`, it should still return the value of its body expression as before.

If it does write to `result`, the generated jump skips the remaining expression code at runtime.

### Why This Is Correct

The semantic rules are enforced before code generation:

- illegal definitions of `result` are rejected
- wrong type of `result` is rejected
- reads from `result` are rejected

Then IR generation gives assignment to `result` return-like behavior:

```text
store assigned value into frame.RV
jump to the function exit label
```

Because the normal function epilogue already returns `frame.RV`, no final assembly changes are needed.

### What Not To Change

Do not make `result` a new keyword in the lexer/parser. It is still syntactically an identifier with special semantic meaning.

Do not treat every variable whose name contains `result` specially. Only the exact name `result`.

Do not allow global `var result`.

Do not allow `result` as a function parameter if you choose the stricter interpretation.

Do not generate a normal memory store for `result = expr`; that would not exit the function.

Do not use `return` syntax unless the language already has it. This exercise is defining return-like behavior through assignment.

### Pitfalls

If you reject all `NameExpr("result")` reads without an exception for assignment left-hand side, then `result = expr` will be impossible to type-check.

If you only change `TypeChecker` and not `ImrGenerator`, the assignment will still behave like an ordinary assignment and the function will not exit.

If you only change `ImrGenerator` and not semantic analysis, invalid programs such as `x = result` may compile.

If you forget to restore `currentFunction` or `currentExitLabel` after nested functions, nested functions can accidentally use the wrong return type or jump to the wrong exit label.

If assignment to `result` appears inside a nested function, it must return from that nested function, not from the outer function.

### Small Test Program

```prev
fun putint(i : int) : void

fun f(x : int) : int =
    let
        var result : int
    in
        if x > 0 then
            result = x + 1
        else
            none,
        x + 100
    end

fun main() : int =
    putint(f(5)),
    0
```

Expected output:

```text
6
```

Also test rejection cases:

```prev
var result : int
```

should be rejected.

```prev
fun f() : int =
    let
        var result : char
    in
        result = 'a'
    end
```

should be rejected because `result` has the wrong type.

```prev
fun f() : int =
    let
        var result : int
    in
        result + 1
    end
```

should be rejected because reading `result` is not allowed.

## Exercise 3: Caller Saves Registers Around Calls

### What It Demands

This is the caller-saved version of the register-saving exercise.

The rule is:

> When a function calls another function, the calling function saves its own registers before the call and restores them after the call.

There are two common choices:

```text
caller-saved:
    caller saves live registers before the call
    caller restores them after the call

callee-saved:
    called function saves the registers it will use in its own prologue
    called function restores them in its own epilogue
```

This version wants the first one: **caller-saved registers**.

In practical terms:

- before every `jal` / `jalr`, save the caller's used physical registers into the caller's frame
- after the call returns, restore those registers
- the callee no longer saves all of its used registers in its prologue

Your current `FinAsm.java` is closer to callee-saved, because it saves `savedRegisters(registerMap)` in every function prologue. For this exercise, move that saving/restoring to the call sites.

### Where It Fits In Prev26

The relevant file is:

- `prev26/src/prev26lang/phase/finasm/FinAsm.java`

Possibly useful for checking:

- `prev26/src/prev26lang/phase/asmgen/AsmGenerator.java`
- `prev26/src/prev26lang/phase/regall/RegAll.java`
- `prev26/src/prev26lang/phase/regall/RegisterAllocator.java`

You usually do not need semantic analysis or IR generation.

### Current Code To Understand First

Currently `AsmGenerator.munchCall` creates this shape:

```java
store arguments
jal / jalr
load result, if needed
```

After register allocation, `FinAsm.emitCodeChunk` renders those instructions one by one.

That is the best place to insert caller saves, because by then you know the physical registers used by the caller:

```java
final LinkedHashMap<MEM.Temp, String> registerMap
```

The current helper:

```java
private Vector<String> savedRegisters(final LinkedHashMap<MEM.Temp, String> registerMap) {
    final LinkedHashSet<String> saved = new LinkedHashSet<String>(registerMap.values());
    return new Vector<String>(saved);
}
```

already gives you the caller's used physical registers when called from the caller's `emitCodeChunk`.

Example:

```text
main uses x5, x6, x7
f uses x5, x10
```

With caller-saved convention, `main` saves `x5`, `x6`, and `x7` before calling `f`, then restores them after `f` returns. It does not matter which registers `f` uses internally.

### Minimal Implementation Plan

1. Keep a save area in the caller's frame.

You can reuse the existing final-frame idea:

```java
final Vector<String> savedRegisters = savedRegisters(registerMap);
final long registerBase = registerSaveBase(codeChunk.frame);
final long frameSize = finalFrameSize(codeChunk.frame, savedRegisters);
```

But interpret the save area differently:

```text
callee-saved version:
    save area stores callee registers during the whole function body

caller-saved version:
    save area stores caller registers only around one call
```

The same save area can be reused for every call inside the function.

2. Stop saving ordinary allocated registers in the callee prologue.

In `emitPrologue`, remove this loop:

```java
for (int i = 0; i < savedRegisters.size(); i++)
    emitStore(savedRegisters.get(i), registerBase + 8L * i);
```

Keep saving old `FP` and `RA` as before:

```java
emitStore("x8", oldFpOffset);
emitStore("x1", returnAddressOffset);
```

Those are special control registers, not ordinary caller-saved working registers.

3. Stop restoring ordinary allocated registers in the callee epilogue.

In `emitEpilogue`, remove this loop:

```java
for (int i = savedRegisters.size() - 1; i >= 0; i--)
    emitLoad(savedRegisters.get(i), registerBase + 8L * i);
```

Keep restoring `RA` and old `FP`:

```java
emitLoad("x1", returnAddressOffset);
emitLoad("x8", oldFpOffset);
```

4. Add helpers for saving/restoring caller registers.

```java
private void emitSaveRegisters(final Vector<String> registers, final long registerBase) {
    for (int i = 0; i < registers.size(); i++)
        emitStore(registers.get(i), registerBase + 8L * i);
}

private void emitRestoreRegisters(final Vector<String> registers, final long registerBase) {
    for (int i = registers.size() - 1; i >= 0; i--)
        emitLoad(registers.get(i), registerBase + 8L * i);
}
```

These helpers store into the current frame, so when used inside caller code they save into the caller's call record.

5. Insert save/restore around call instructions in `emitCodeChunk`.

Inside the instruction loop:

```java
for (final ASM.Instruction instruction : codeChunk.instructions()) {
    final String rendered = render(instruction, registerMap);
    if (rendered == null)
        continue;

    if (instruction.controlFlow == ASM.ControlFlow.CALL) {
        emitSaveRegisters(savedRegisters, registerBase);
        lines.add(indentIfInstruction(rendered));
        emitRestoreRegisters(savedRegisters, registerBase);
        continue;
    }

    ...
}
```

If `controlFlow` is not publicly accessible, add a tiny getter to `ASM.Instruction`, for example:

```java
public ASM.ControlFlow controlFlow() {
    return controlFlow;
}
```

and use:

```java
instruction.controlFlow() == ASM.ControlFlow.CALL
```

6. Keep the save area included in the caller frame size.

Keep:

```java
private long finalFrameSize(final MEM.Frame frame, final Vector<String> savedRegisters) {
    return frame.size + 8L * savedRegisters.size();
}
```

Even though the registers are no longer saved in the prologue, the caller still needs memory space for saving them around calls.

7. Decide which caller registers to save.

The simplest correct version saves all physical registers used by the caller:

```java
final LinkedHashSet<String> saved = new LinkedHashSet<String>(registerMap.values());
```

This is conservative but safe.

A more optimized version would save only registers that are live across that specific call. That requires liveness information in `FinAsm`, so it is more work. For an exam-style solution, saving all caller-used registers around every call is usually the simplest correct implementation.

8. Keep runtime functions simple.

With caller-saved convention, hand-written runtime functions such as `_putint`, `_getint`, `_putchar`, and `_new` do not need to preserve allocatable registers for the caller. The caller saves its own registers before calling them.

So you can remove `emitRuntimeSave()` / `emitRuntimeRestore()` from runtime routines if all calls to runtime routines go through the normal generated `CALL` instruction path.

For example, `_putint` can be:

```asm
_putint:
  ld x10, 8(x2)       # adjust offset to your current calling convention
  addi x17, x0, 1
  ecall
  jalr x0, x1, 0
```

If you keep `emitRuntimeSave()` anyway, the program is still safe, just more redundant.

### Why This Is Correct

Suppose caller `g` has important values in `x5` and `x6`, and it calls function `f`.

Before the call, `g` stores its own registers:

```asm
sd x5, offset1(sp)
sd x6, offset2(sp)
jal x1, _f
ld x6, offset2(sp)
ld x5, offset1(sp)
```

Now `f` is free to use `x5` and `x6` however it wants. When `f` returns, `g` restores the values it needs.

So the responsibility belongs to the function that makes the call.

That is exactly caller-saved convention.

### Important Ordering Detail

`AsmGenerator.munchCall` emits:

```text
store arguments
CALL
load result
```

In `FinAsm`, save/restore should wrap only the actual `CALL` instruction:

```asm
store arguments
save caller registers
jal / jalr
restore caller registers
load result
```

This is good because:

- arguments are already safely stored before the call
- the result is loaded after the caller registers are restored
- if the result destination uses one of the restored registers, the later result load overwrites it correctly

### What Not To Change

Do not save ordinary allocated registers in both caller and callee. That is redundant.

Do not save fixed registers such as `SP` / `x2` or `FP` / `x8` through the caller-saved register list.

Do not add `RA` / `x1` to ordinary allocatable registers. It is still handled by the call/prologue convention.

Do not save every physical register if you can save only `registerMap.values()`.

Do not insert save/restore before argument stores. Save after arguments are stored and immediately before the call.

### Pitfalls

If `registerBase` is computed incorrectly, saved registers can overlap with spills, locals, old `FP`, or `RA`.

If `finalFrameSize` does not include `8 * savedRegisters.size()`, caller-side saves can write outside the allocated frame.

If you restore caller registers before the callee returns, obviously nothing works. The restore must be after the `jal` / `jalr`.

If you restore after the call-result load, you can overwrite the loaded result if it was placed in a restored register.

If you remove runtime save/restore but some runtime function is called manually outside the normal generated call path, then it can clobber caller registers. Normal PREV calls are fine.

### Small Test Program

Use a program where the caller has values that must survive a call:

```prev
fun putint(i : int) : void

fun f(a : int, b : int) : int =
    a + b

fun g(x : int) : int =
    let
        var a : int
        var b : int
        var c : int
    in
        a = x + 1,
        b = x + 2,
        c = f(a, b),
        a + b + c
    end

fun main() : int =
    putint(g(10)),
    0
```

Expected output:

```text
46
```

Recommended command:

```sh
make -C prev26 bin
java -cp prev26/bin:prev26/src:prev26/lib/antlr-4.13.2-complete.jar prev26lang.Compiler \
  --logged-phase=none \
  --target-phase=finasm \
  --num-regs=2 \
  --dst-file-name=/tmp/caller-saved-test.asm \
  path/to/test.p26
```

In the generated assembly, check that `_g` saves/restores its registers around:

```asm
jal x1, _f
```

and that `_f` does not save all of its allocated registers in its own prologue.

## Exercise 3: Force Addition And Subtraction Results Into One Register

### What It Demands

The exercise says to change the intermediate-code generator so that the result of every addition or subtraction is always computed in register `$1`.

It also says to try to use register `$1` for other operations too.

For your Prev26 compiler, be careful with the wording:

- old exercises often used `$1` as an abstract machine/intermediate-code register name
- in your RISC-V backend, physical `x1` is `RA`, the return-address register
- therefore, do not literally use RISC-V `x1` for arithmetic results

The safe Prev26 interpretation is:

```text
choose one fixed working register,
force every ADD/SUB instruction to write its result there,
then move the value onward if the surrounding code expects a normal temporary
```

For example, choose `x4` as the special register, because it is not currently in `ALLOCATABLE_REGISTERS` and is not used as `SP`, `FP`, or `RA`.

### Where It Fits In Prev26

The relevant files are:

- `prev26/src/prev26lang/phase/memory/MEM.java`
- `prev26/src/prev26lang/phase/regall/RegAll.java`
- `prev26/src/prev26lang/phase/asmgen/AsmGenerator.java`

Despite the exercise saying "intermediate-code generator", in this compiler the cleanest place is `AsmGenerator`, because this is where an `IMR.BINOP.ADD` or `IMR.BINOP.SUB` becomes a register-to-register instruction.

Changing `ImrGenerator.java` alone would not force a physical register; it only builds tree-shaped IMR expressions.

### Current Code To Understand First

Currently, `AsmGenerator.munchExpr` creates a fresh temporary for expression results:

```java
final MEM.Temp dst = new MEM.Temp();
munchExprInto(dst, expr);
return dst;
```

Then `munchBinop` emits normal operations into whichever temporary was requested:

```java
case ADD -> emit("add *d0, *s0, *s1", temps(dst), temps(fst, snd), new Vector<MEM.Label>());
case SUB -> emit("sub *d0, *s0, *s1", temps(dst), temps(fst, snd), new Vector<MEM.Label>());
```

Register allocation later decides which physical register `dst` gets.

The exercise wants `ADD` and `SUB` to be computed in one fixed register instead of an arbitrary allocated register.

### Minimal Implementation Plan

1. Add a fixed temporary in `MEM.java`.

Add near `SP`, `FP`, and `RA`:

```java
/** Fixed temporary used for forced ADD/SUB results. */
public static final Temp R1 = new Temp("R1");
```

This is the compiler's abstract "register `$1`".

2. Map that fixed temporary to a safe physical register in `RegAll.java`.

Update `isFixedRegister`:

```java
return temp.equals(MEM.SP)
    || temp.equals(MEM.FP)
    || temp.equals(MEM.RA)
    || temp.equals(MEM.R1);
```

Update `fixedRegisterName`:

```java
if (temp.equals(MEM.R1))
    return "x4";
```

Do not map it to RISC-V `x1`, because `x1` is the return-address register.

Using `x4` is the shortest safe option because the allocator currently ignores fixed registers in the interference graph. If you mapped `MEM.R1` to an allocatable register such as `x5`, the allocator could also assign `x5` to an ordinary temporary at the same time, causing incorrect code.

3. Force `ADD` and `SUB` to write to `MEM.R1`.

In `AsmGenerator.munchBinop`, change the `ADD` and `SUB` cases.

Instead of:

```java
case ADD -> emit("add *d0, *s0, *s1", temps(dst), temps(fst, snd), new Vector<MEM.Label>());
case SUB -> emit("sub *d0, *s0, *s1", temps(dst), temps(fst, snd), new Vector<MEM.Label>());
```

use:

```java
case ADD -> {
    emit("add *d0, *s0, *s1", temps(MEM.R1), temps(fst, snd), new Vector<MEM.Label>());
    moveFromR1(dst);
}
case SUB -> {
    emit("sub *d0, *s0, *s1", temps(MEM.R1), temps(fst, snd), new Vector<MEM.Label>());
    moveFromR1(dst);
}
```

Add the helper:

```java
private void moveFromR1(final MEM.Temp dst) {
    if (!dst.equals(MEM.R1))
        emit(ASM.move("addi *d0, *s0, 0", dst, MEM.R1));
}
```

This ensures the actual arithmetic instruction writes to the special register, while the rest of the compiler can still receive the value in the expected destination temporary.

4. Why the move is necessary.

For nested expressions, this is important:

```prev
(a + b) + (c + d)
```

Both inner additions must compute through `R1`. If the left inner result stayed only in `R1`, the right inner addition would overwrite it.

So the safe sequence is:

```text
R1 = a + b
t1 = R1
R1 = c + d
t2 = R1
R1 = t1 + t2
t3 = R1
```

Yes, this creates extra moves. That is okay for the simple correct solution.

5. Optional: use `R1` for other operations too.

The exercise says to "try" to use `$1` for other operations too. After ADD/SUB works, you can extend the same pattern to more operations:

```java
case MUL -> {
    emit("mul *d0, *s0, *s1", temps(MEM.R1), temps(fst, snd), new Vector<MEM.Label>());
    moveFromR1(dst);
}
case DIV -> {
    emit("div *d0, *s0, *s1", temps(MEM.R1), temps(fst, snd), new Vector<MEM.Label>());
    moveFromR1(dst);
}
```

You can also do it for `AND`, `OR`, comparisons, and unary negation if you want. Keep it simple: always compute into `MEM.R1`, then move out if needed.

Do this only after the required ADD/SUB behavior is working.

6. Optional advanced version: make the special register allocatable when safe.

The phrase "use `$1` also for other operations" can also mean:

```text
do not reserve the register completely;
allow normal register allocation to use it when it does not interfere
```

That is more complex in this compiler, because fixed registers are currently excluded from the interference graph:

```java
if (RegAll.isFixedRegister(temp))
    return null;
```

To support this properly, you would need precolored graph nodes:

- add `MEM.R1` to the interference graph as a node with a fixed color
- make ordinary temps that interfere with `MEM.R1` unable to choose that color
- allow non-interfering temps to still choose the same physical register

This is elegant but not the shortest exam solution. The simple safe solution with `x4` is much easier.

### Why This Is Correct

Every ADD/SUB assembly instruction now has the special fixed temporary as its destination:

```asm
add R1, left, right
sub R1, left, right
```

During final rendering, `R1` becomes the chosen physical register, for example `x4`.

Then the generated move copies the value into the temporary expected by surrounding code. That keeps nested expressions, memory stores, function arguments, and return values working normally.

### What Not To Change

Do not use physical `x1` in Prev26. It is `RA`.

Do not map `MEM.R1` to a register that is still normally allocatable unless you also implement proper precolored-register interference.

Do not remove the move from `R1` to `dst`; nested expressions will break.

Do not change semantic analysis or memory layout.

Do not force only the final expression result into `R1`; the requirement is every addition and subtraction.

### Pitfalls

If you compute the left operand of an addition into `R1`, then compute the right operand and it also uses `R1`, the left value can be overwritten. Let `munchExpr` produce ordinary temporaries for operands, then emit the final ADD/SUB into `R1`.

If `MEM.R1` is treated as an ordinary temporary, register allocation may spill or recolor it. It must be fixed.

If `MEM.R1` is fixed to an allocatable physical register without allocator support, another live temporary can be allocated to the same register.

If you use `x4` in hand-written runtime code too, be careful not to rely on it surviving across compiled code unless you save/restore it. In the minimal solution, `x4` is only a scratch-like fixed arithmetic result register.

### Small Test Program

```prev
fun putint(i : int) : void

fun main() : int =
    putint((1 + 2) + (10 - 3)),
    0
```

Expected output:

```text
10
```

Recommended command:

```sh
make -C prev26 bin
java -cp prev26/bin:prev26/src:prev26/lib/antlr-4.13.2-complete.jar prev26lang.Compiler \
  --logged-phase=none \
  --target-phase=finasm \
  --num-regs=2 \
  --dst-file-name=/tmp/r1-add-sub-test.asm \
  path/to/test.p26
```

Inspect the generated assembly:

```text
add x4, ...
sub x4, ...
```

Every actual addition/subtraction result should first appear in `x4`.

## Exercise 2: Short-Circuit Conditions With `and` And `or`

### What It Demands

The exercise says to change the compiler so that `and` and `or` use short-circuit evaluation only when they appear in the condition of an `if` or `while`.

The required behavior is:

```text
x or y   = true  if x == true, otherwise y
x and y  = false if x == false, otherwise y
```

So the right operand must not be evaluated when the left operand already decides the result.

The two important restrictions are:

- If `and` or `or` is used somewhere else, keep the current ordinary/eager evaluation.
- Do not change the interpreter.

That points to `imrgen`, not to `imrlin.Interpreter` and not to the normal `IMR.BINOP(AND/OR)` implementation.

### Where It Fits In Prev26

The relevant file is:

- `prev26/src/prev26lang/phase/imrgen/ImrGenerator.java`

Useful files for understanding why this is enough:

- `prev26/src/prev26lang/phase/imrgen/IMR.java`
- `prev26/src/prev26lang/phase/imrlin/ImrLinearizer.java`
- `prev26/src/prev26lang/phase/asmgen/AsmGenerator.java`
- `prev26/src/prev26lang/phase/imrlin/Interpreter.java`

You should not need to change lexical analysis, syntax analysis, semantic analysis, memory layout, ordinary assembly generation, register allocation, or final assembly.

### Current Behavior To Understand First

The current `visit(AST.BinExpr)` always visits both operands and builds one `IMR.BINOP`:

```java
@Override
public Object visit(final AST.BinExpr binExpr, final Object arg) {
    binExpr.fstExpr.accept(this, arg);
    binExpr.sndExpr.accept(this, arg);

    putExprIR(binExpr, new IMR.BINOP(
        binOper(binExpr.oper),
        requireExprIR(binExpr.fstExpr),
        requireExprIR(binExpr.sndExpr)
    ));
    return null;
}
```

That is correct for ordinary expression use and should stay unchanged.

The current `if` and `while` visitors first generate the condition as a value and then use that value in a `CJUMP`:

```java
body.add(new IMR.CJUMP(requireExprIR(ifThenExpr.condExpr), new IMR.NAME(thenLabel), new IMR.NAME(endLabel)));
```

For a condition like:

```prev
if x or y then ...
```

this means `x or y` is first translated as an eager `BINOP(OR, x, y)`, so `y` is evaluated even when `x` is already true.

### Minimal Implementation Plan

1. Add a helper that emits a conditional jump directly from an AST condition.

In `ImrGenerator.java`, add this helper near the existing expression helpers:

```java
private void emitCondJump(
    final AST.Expr condExpr,
    final MEM.Label trueLabel,
    final MEM.Label falseLabel,
    final Vector<IMR.Stmt> body,
    final Object arg
) {
    if (condExpr == null || trueLabel == null || falseLabel == null || body == null)
        throw new Report.InternalError();

    if (condExpr instanceof AST.BinExpr binExpr) {
        switch (binExpr.oper) {
        case OR -> {
            final MEM.Label rightLabel = new MEM.Label();
            emitCondJump(binExpr.fstExpr, trueLabel, rightLabel, body, arg);
            body.add(new IMR.LABEL(rightLabel));
            emitCondJump(binExpr.sndExpr, trueLabel, falseLabel, body, arg);
            return;
        }
        case AND -> {
            final MEM.Label rightLabel = new MEM.Label();
            emitCondJump(binExpr.fstExpr, rightLabel, falseLabel, body, arg);
            body.add(new IMR.LABEL(rightLabel));
            emitCondJump(binExpr.sndExpr, trueLabel, falseLabel, body, arg);
            return;
        }
        default -> {
            // Other binary operators are ordinary expressions.
        }
        }
    }

    condExpr.accept(this, arg);
    body.add(new IMR.CJUMP(requireExprIR(condExpr), new IMR.NAME(trueLabel), new IMR.NAME(falseLabel)));
}
```

The shape is the whole solution:

```text
x or y:
    if x jump TRUE else jump RIGHT
RIGHT:
    if y jump TRUE else jump FALSE

x and y:
    if x jump RIGHT else jump FALSE
RIGHT:
    if y jump TRUE else jump FALSE
```

Because the helper is recursive, nested conditions also work:

```prev
if a and b or c then ...
```

The parser already builds this as nested `AST.BinExpr` nodes, so the helper just follows the AST.

2. Use the helper in `IfThenExpr`.

Change `visit(AST.IfThenExpr)` so it does not eagerly translate the condition with `condExpr.accept(...)`.

Replace the condition part with `emitCondJump(...)`:

```java
@Override
public Object visit(final AST.IfThenExpr ifThenExpr, final Object arg) {
    ifThenExpr.thenExpr.accept(this, arg);

    final MEM.Label thenLabel = new MEM.Label();
    final MEM.Label endLabel = new MEM.Label();

    final Vector<IMR.Stmt> body = stmts();
    emitCondJump(ifThenExpr.condExpr, thenLabel, endLabel, body, arg);
    body.add(new IMR.LABEL(thenLabel));
    body.add(new IMR.ESTMT(requireExprIR(ifThenExpr.thenExpr)));
    body.add(new IMR.LABEL(endLabel));

    putExprIR(ifThenExpr, sexpr(body, new IMR.CONST(0)));
    return null;
}
```

The condition is still translated, but only through `emitCondJump`, which can choose between short-circuit control flow and ordinary expression evaluation.

3. Use the helper in `IfThenElseExpr`.

Change the beginning of `visit(AST.IfThenElseExpr)` in the same way:

```java
@Override
public Object visit(final AST.IfThenElseExpr ifThenElseExpr, final Object arg) {
    ifThenElseExpr.thenExpr.accept(this, arg);
    ifThenElseExpr.elseExpr.accept(this, arg);

    final MEM.Label thenLabel = new MEM.Label();
    final MEM.Label elseLabel = new MEM.Label();
    final MEM.Label endLabel = new MEM.Label();

    final Vector<IMR.Stmt> body = stmts();
    emitCondJump(ifThenElseExpr.condExpr, thenLabel, elseLabel, body, arg);
    body.add(new IMR.LABEL(thenLabel));
    body.add(new IMR.ESTMT(requireExprIR(ifThenElseExpr.thenExpr)));
    body.add(new IMR.JUMP(new IMR.NAME(endLabel)));
    body.add(new IMR.LABEL(elseLabel));
    body.add(new IMR.ESTMT(requireExprIR(ifThenElseExpr.elseExpr)));
    body.add(new IMR.LABEL(endLabel));

    putExprIR(ifThenElseExpr, sexpr(body, new IMR.CONST(0)));
    return null;
}
```

4. Use the helper in `WhileExpr`.

Change `visit(AST.WhileExpr)` so the loop condition is also emitted as control flow:

```java
@Override
public Object visit(final AST.WhileExpr whileExpr, final Object arg) {
    whileExpr.expr.accept(this, arg);

    final MEM.Label condLabel = new MEM.Label();
    final MEM.Label bodyLabel = new MEM.Label();
    final MEM.Label endLabel = new MEM.Label();

    final Vector<IMR.Stmt> body = stmts();
    body.add(new IMR.LABEL(condLabel));
    emitCondJump(whileExpr.condExpr, bodyLabel, endLabel, body, arg);
    body.add(new IMR.LABEL(bodyLabel));
    body.add(new IMR.ESTMT(requireExprIR(whileExpr.expr)));
    body.add(new IMR.JUMP(new IMR.NAME(condLabel)));
    body.add(new IMR.LABEL(endLabel));

    putExprIR(whileExpr, sexpr(body, new IMR.CONST(0)));
    return null;
}
```

5. Leave normal binary expressions alone.

Do not change:

```java
public Object visit(final AST.BinExpr binExpr, final Object arg)
```

This is what keeps the required "elsewhere use ordinary evaluation" rule. For example:

```prev
var b : bool
b = left() or right()
```

should still be translated as the normal eager `IMR.BINOP(OR, ...)`.

### Why This Is Correct

The interpreter evaluates `IMR.BINOP(AND/OR)` eagerly:

```java
Long fstExpr = imrBinop.fstExpr.accept(this, null);
Long sndExpr = imrBinop.sndExpr.accept(this, null);
```

The exercise says not to change the interpreter, so the correct move is to avoid using `IMR.BINOP(AND/OR)` for the special condition case.

In generated final code, an `if` or `while` condition does not actually need a boolean value stored in a temporary. It only needs to choose between two labels. That is exactly what `IMR.CJUMP` already represents, so the short-circuit version can be expressed entirely as labels and conditional jumps.

For `or`, the left operand jumps straight to the true label when it is true. The right operand is evaluated only from the left operand's false path.

For `and`, the left operand jumps straight to the false label when it is false. The right operand is evaluated only from the left operand's true path.

### What Not To Change

Do not change `imrlin.Interpreter`.

Do not change `AsmGenerator.munchBinop` to short-circuit `AND` and `OR`, because that would affect every use of these operators, including assignments, arguments, and larger expressions.

Do not change `IMR.BINOP` semantics. Its documentation says that it evaluates both operands, and several phases rely on that simple expression shape.

Do not remove or special-case semantic checks. `and` and `or` still require boolean operands and still produce a boolean result.

Do not pre-run `condExpr.accept(this, arg)` in the `if`/`while` visitors before calling `emitCondJump`; the helper owns condition translation. Pre-running it makes it too easy to accidentally keep the old eager condition around.

### Pitfalls

If you only change `visit(AST.BinExpr)`, all uses of `and` and `or` will short-circuit, not only `if` and `while` conditions.

If you leave the old `condExpr.accept(this, arg)` and still use `requireExprIR(condExpr)` in the `CJUMP`, the condition will remain eager.

If you generate `x or y` as "evaluate x into a temp, evaluate y into a temp, then branch", the right operand is still evaluated, so the exercise is not solved.

If you forget to recurse in `emitCondJump`, nested expressions such as `a and b or c` will only short-circuit at the top level.

If the true and false labels are accidentally swapped in the `AND` case, `x and y` behaves like "if x is false, continue with y", which is the opposite of the required semantics.

### Small Test Program

Use side effects in the right operand so the test can prove whether the right side ran:

```prev
fun putint(i : int) : void

var hits : int

fun mark() : bool =
    hits = hits + 1,
    true

fun main() : int =
    hits = 0,

    if true or mark() then
        none
    end,
    putint(hits),

    if false and mark() then
        none
    end,
    putint(hits),

    if false or mark() then
        none
    end,
    putint(hits),

    0
```

Expected output:

```text
0
0
1
```

Also test `while`, because it uses the same helper but has a loop back edge:

```prev
fun putint(i : int) : void

var i : int
var hits : int

fun mark() : bool =
    hits = hits + 1,
    true

fun main() : int =
    i = 0,
    hits = 0,
    while i < 3 and mark() do
        i = i + 1
    end,
    putint(i),
    putint(hits),
    while false and mark() do
        i = i + 1
    end,
    putint(hits),
    0
```

Expected output:

```text
3
3
3
```

Recommended command:

```sh
make -C prev26 bin
java -cp prev26/bin:prev26/src:prev26/lib/antlr-4.13.2-complete.jar prev26lang.Compiler \
  --logged-phase=none \
  --target-phase=finasm \
  --dst-file-name=/tmp/short-circuit-test.asm \
  path/to/test.p26
```

Inspecting the generated IMR or assembly should show labels and branches between the left and right operand, instead of one eager `AND`/`OR` value calculation for the condition.
