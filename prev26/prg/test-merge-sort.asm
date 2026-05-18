.data
_n:
  .space 8
_arr:
  .space 800
_tmp:
  .space 800
L0:
  .byte 73, 110, 112, 117, 116, 32, 116, 104, 101, 32, 97, 114, 114, 97, 121, 32, 108, 101, 110, 103, 116, 104, 58
.align 3
L1:
  .byte 73, 110, 112, 117, 116, 32, 116, 104, 101, 32, 97, 114, 114, 97, 121, 58
.align 3
L2:
  .byte 83, 111, 114, 116, 101, 100, 32, 98, 121, 32, 109, 101, 114, 103, 101, 32, 115, 111, 114, 116, 58
.align 3

.text
.globl main
main:
  addi x2, x2, -16
  sd x0, 0(x2)
  jal x1, _main
  ld x10, 0(x2)
  addi x2, x2, 16
  addi x17, x0, 10
  ecall

_printString:
  addi x2, x2, -80
  sd x5, 16(x2)
  sd x6, 24(x2)
  sd x7, 32(x2)
  sd x9, 40(x2)
  sd x8, 48(x2)
  sd x1, 56(x2)
  addi x8, x2, 80
  jal x0, L3
L3:
  addi x6, x8, 0
  addi x5, x0, -8
  add x6, x6, x5
  addi x5, x0, 0
  sd x5, 0(x6)
L5:
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x7, 0(x5)
  addi x6, x8, 0
  addi x5, x0, 16
  add x5, x6, x5
  ld x5, 0(x5)
  slt x5, x7, x5
  bne x5, x0, L6
  jal x0, L7
L6:
  addi x6, x8, 0
  addi x5, x0, -16
  add x9, x6, x5
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x7, 0(x5)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x5, 0(x5)
  add x5, x7, x5
  lbu x5, 0(x5)
  sb x5, 0(x9)
  addi x6, x8, 0
  addi x5, x0, -8
  add x7, x6, x5
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x5, x6, x5
  sd x5, 0(x7)
  addi x7, x0, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  lbu x5, 0(x5)
  sd x7, 0(x2)
  sd x5, 8(x2)
  jal x1, _putchar
  ld x5, 0(x2)
  jal x0, L5
L7:
  addi x5, x0, 0
  jal x0, L4
L4:
  sd x5, 0(x8)
  ld x9, 40(x2)
  ld x7, 32(x2)
  ld x6, 24(x2)
  ld x5, 16(x2)
  ld x1, 56(x2)
  ld x8, 48(x2)
  addi x2, x2, 80
  jalr x0, x1, 0

_printArray:
  addi x2, x2, -72
  sd x5, 16(x2)
  sd x6, 24(x2)
  sd x7, 32(x2)
  sd x9, 40(x2)
  sd x8, 48(x2)
  sd x1, 56(x2)
  addi x8, x2, 72
  jal x0, L8
L8:
  addi x6, x8, 0
  addi x5, x0, -8
  add x6, x6, x5
  addi x5, x0, 0
  sd x5, 0(x6)
L12:
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x7, 0(x5)
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x5, 0(x5)
  slt x5, x7, x5
  bne x5, x0, L13
  jal x0, L14
L13:
  addi x9, x0, 238
  slli x9, x9, 8
  addi x9, x9, 238
  slli x9, x9, 8
  addi x9, x9, 238
  slli x9, x9, 8
  addi x9, x9, 238
  lui x7, %hi(_arr)
  addi x7, x7, %lo(_arr)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x5, x6, x5
  add x5, x7, x5
  ld x5, 0(x5)
  sd x9, 0(x2)
  sd x5, 8(x2)
  jal x1, _putint
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x7, 0(x5)
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  sub x5, x6, x5
  slt x5, x7, x5
  bne x5, x0, L10
  jal x0, L11
L10:
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  addi x5, x0, 32
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _putchar
L11:
  addi x6, x8, 0
  addi x5, x0, -8
  add x7, x6, x5
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x5, x6, x5
  sd x5, 0(x7)
  jal x0, L12
L14:
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  addi x5, x0, 10
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _putchar
  ld x5, 0(x2)
  jal x0, L9
L9:
  sd x5, 0(x8)
  ld x9, 40(x2)
  ld x7, 32(x2)
  ld x6, 24(x2)
  ld x5, 16(x2)
  ld x1, 56(x2)
  ld x8, 48(x2)
  addi x2, x2, 72
  jalr x0, x1, 0

_merge:
  addi x2, x2, -72
  sd x5, 0(x2)
  sd x6, 8(x2)
  sd x7, 16(x2)
  sd x9, 24(x2)
  sd x8, 32(x2)
  sd x1, 40(x2)
  addi x8, x2, 72
  jal x0, L15
L15:
  addi x6, x8, 0
  addi x5, x0, -8
  add x7, x6, x5
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x5, 0(x5)
  sd x5, 0(x7)
  addi x6, x8, 0
  addi x5, x0, -16
  add x7, x6, x5
  addi x6, x8, 0
  addi x5, x0, 16
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x5, x6, x5
  sd x5, 0(x7)
  addi x6, x8, 0
  addi x5, x0, -24
  add x7, x6, x5
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x5, 0(x5)
  sd x5, 0(x7)
L20:
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x7, 0(x5)
  addi x6, x8, 0
  addi x5, x0, 16
  add x5, x6, x5
  ld x5, 0(x5)
  slt x5, x5, x7
  xori x9, x5, 1
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  ld x7, 0(x5)
  addi x6, x8, 0
  addi x5, x0, 24
  add x5, x6, x5
  ld x5, 0(x5)
  slt x5, x5, x7
  xori x5, x5, 1
  sltu x6, x0, x9
  sltu x5, x0, x5
  and x5, x6, x5
  bne x5, x0, L21
  jal x0, L22
L21:
  lui x7, %hi(_arr)
  addi x7, x7, %lo(_arr)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x5, x6, x5
  add x5, x7, x5
  ld x9, 0(x5)
  lui x7, %hi(_arr)
  addi x7, x7, %lo(_arr)
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x5, x6, x5
  add x5, x7, x5
  ld x5, 0(x5)
  slt x5, x5, x9
  xori x5, x5, 1
  bne x5, x0, L17
  jal x0, L18
L17:
  lui x7, %hi(_tmp)
  addi x7, x7, %lo(_tmp)
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x5, x6, x5
  add x9, x7, x5
  lui x7, %hi(_arr)
  addi x7, x7, %lo(_arr)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x5, x6, x5
  add x5, x7, x5
  ld x5, 0(x5)
  sd x5, 0(x9)
  addi x6, x8, 0
  addi x5, x0, -8
  add x7, x6, x5
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x5, x6, x5
  sd x5, 0(x7)
  jal x0, L19
L18:
  lui x7, %hi(_tmp)
  addi x7, x7, %lo(_tmp)
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x5, x6, x5
  add x9, x7, x5
  lui x7, %hi(_arr)
  addi x7, x7, %lo(_arr)
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x5, x6, x5
  add x5, x7, x5
  ld x5, 0(x5)
  sd x5, 0(x9)
  addi x6, x8, 0
  addi x5, x0, -16
  add x7, x6, x5
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x5, x6, x5
  sd x5, 0(x7)
L19:
  addi x6, x8, 0
  addi x5, x0, -24
  add x7, x6, x5
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x5, x6, x5
  sd x5, 0(x7)
  jal x0, L20
L22:
L23:
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x7, 0(x5)
  addi x6, x8, 0
  addi x5, x0, 16
  add x5, x6, x5
  ld x5, 0(x5)
  slt x5, x5, x7
  xori x5, x5, 1
  bne x5, x0, L24
  jal x0, L25
L24:
  lui x7, %hi(_tmp)
  addi x7, x7, %lo(_tmp)
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x5, x6, x5
  add x9, x7, x5
  lui x7, %hi(_arr)
  addi x7, x7, %lo(_arr)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x5, x6, x5
  add x5, x7, x5
  ld x5, 0(x5)
  sd x5, 0(x9)
  addi x6, x8, 0
  addi x5, x0, -8
  add x7, x6, x5
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x5, x6, x5
  sd x5, 0(x7)
  addi x6, x8, 0
  addi x5, x0, -24
  add x7, x6, x5
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x5, x6, x5
  sd x5, 0(x7)
  jal x0, L23
L25:
L26:
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  ld x7, 0(x5)
  addi x6, x8, 0
  addi x5, x0, 24
  add x5, x6, x5
  ld x5, 0(x5)
  slt x5, x5, x7
  xori x5, x5, 1
  bne x5, x0, L27
  jal x0, L28
L27:
  lui x7, %hi(_tmp)
  addi x7, x7, %lo(_tmp)
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x5, x6, x5
  add x9, x7, x5
  lui x7, %hi(_arr)
  addi x7, x7, %lo(_arr)
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x5, x6, x5
  add x5, x7, x5
  ld x5, 0(x5)
  sd x5, 0(x9)
  addi x6, x8, 0
  addi x5, x0, -16
  add x7, x6, x5
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x5, x6, x5
  sd x5, 0(x7)
  addi x6, x8, 0
  addi x5, x0, -24
  add x7, x6, x5
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x5, x6, x5
  sd x5, 0(x7)
  jal x0, L26
L28:
  addi x6, x8, 0
  addi x5, x0, -24
  add x7, x6, x5
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x5, 0(x5)
  sd x5, 0(x7)
L29:
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x7, 0(x5)
  addi x6, x8, 0
  addi x5, x0, 24
  add x5, x6, x5
  ld x5, 0(x5)
  slt x5, x5, x7
  xori x5, x5, 1
  bne x5, x0, L30
  jal x0, L31
L30:
  lui x7, %hi(_arr)
  addi x7, x7, %lo(_arr)
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x5, x6, x5
  add x9, x7, x5
  lui x7, %hi(_tmp)
  addi x7, x7, %lo(_tmp)
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x5, x6, x5
  add x5, x7, x5
  ld x5, 0(x5)
  sd x5, 0(x9)
  addi x6, x8, 0
  addi x5, x0, -24
  add x7, x6, x5
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x5, x6, x5
  sd x5, 0(x7)
  jal x0, L29
L31:
  addi x5, x0, 0
  jal x0, L16
L16:
  sd x5, 0(x8)
  ld x9, 24(x2)
  ld x7, 16(x2)
  ld x6, 8(x2)
  ld x5, 0(x2)
  ld x1, 40(x2)
  ld x8, 32(x2)
  addi x2, x2, 72
  jalr x0, x1, 0

_mergeSort:
  addi x2, x2, -96
  sd x5, 32(x2)
  sd x6, 40(x2)
  sd x7, 48(x2)
  sd x9, 56(x2)
  sd x10, 64(x2)
  sd x8, 72(x2)
  sd x1, 80(x2)
  addi x8, x2, 96
  jal x0, L32
L32:
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x7, 0(x5)
  addi x6, x8, 0
  addi x5, x0, 16
  add x5, x6, x5
  ld x5, 0(x5)
  slt x5, x7, x5
  bne x5, x0, L34
  jal x0, L35
L34:
  addi x6, x8, 0
  addi x5, x0, -8
  add x9, x6, x5
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x7, 0(x5)
  addi x6, x8, 0
  addi x5, x0, 16
  add x5, x6, x5
  ld x5, 0(x5)
  add x6, x7, x5
  addi x5, x0, 2
  div x5, x6, x5
  sd x5, 0(x9)
  addi x9, x0, 238
  slli x9, x9, 8
  addi x9, x9, 238
  slli x9, x9, 8
  addi x9, x9, 238
  slli x9, x9, 8
  addi x9, x9, 238
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x7, 0(x5)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x5, 0(x5)
  sd x9, 0(x2)
  sd x7, 8(x2)
  sd x5, 16(x2)
  jal x1, _mergeSort
  addi x9, x0, 238
  slli x9, x9, 8
  addi x9, x9, 238
  slli x9, x9, 8
  addi x9, x9, 238
  slli x9, x9, 8
  addi x9, x9, 238
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x7, x6, x5
  addi x6, x8, 0
  addi x5, x0, 16
  add x5, x6, x5
  ld x5, 0(x5)
  sd x9, 0(x2)
  sd x7, 8(x2)
  sd x5, 16(x2)
  jal x1, _mergeSort
  addi x10, x0, 238
  slli x10, x10, 8
  addi x10, x10, 238
  slli x10, x10, 8
  addi x10, x10, 238
  slli x10, x10, 8
  addi x10, x10, 238
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x9, 0(x5)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x7, 0(x5)
  addi x6, x8, 0
  addi x5, x0, 16
  add x5, x6, x5
  ld x5, 0(x5)
  sd x10, 0(x2)
  sd x9, 8(x2)
  sd x7, 16(x2)
  sd x5, 24(x2)
  jal x1, _merge
  ld x5, 0(x2)
L35:
  addi x5, x0, 0
  jal x0, L33
L33:
  sd x5, 0(x8)
  ld x10, 64(x2)
  ld x9, 56(x2)
  ld x7, 48(x2)
  ld x6, 40(x2)
  ld x5, 32(x2)
  ld x1, 80(x2)
  ld x8, 72(x2)
  addi x2, x2, 96
  jalr x0, x1, 0

_main:
  addi x2, x2, -80
  sd x5, 24(x2)
  sd x6, 32(x2)
  sd x7, 40(x2)
  sd x9, 48(x2)
  sd x8, 56(x2)
  sd x1, 64(x2)
  addi x8, x2, 80
  jal x0, L36
L36:
  addi x7, x0, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  lui x6, %hi(L0)
  addi x6, x6, %lo(L0)
  addi x5, x0, 23
  sd x7, 0(x2)
  sd x6, 8(x2)
  sd x5, 16(x2)
  jal x1, _printString
  lui x6, %hi(_n)
  addi x6, x6, %lo(_n)
  addi x5, x0, 238
  slli x5, x5, 8
  addi x5, x5, 238
  slli x5, x5, 8
  addi x5, x5, 238
  slli x5, x5, 8
  addi x5, x5, 238
  sd x5, 0(x2)
  jal x1, _getint
  ld x5, 0(x2)
  sd x5, 0(x6)
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  addi x5, x0, 10
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _putchar
  lui x5, %hi(_n)
  addi x5, x5, %lo(_n)
  ld x6, 0(x5)
  addi x5, x0, 0
  slt x5, x6, x5
  bne x5, x0, L38
  jal x0, L39
L38:
  lui x6, %hi(_n)
  addi x6, x6, %lo(_n)
  addi x5, x0, 0
  sd x5, 0(x6)
L39:
  lui x5, %hi(_n)
  addi x5, x5, %lo(_n)
  ld x6, 0(x5)
  addi x5, x0, 100
  slt x5, x5, x6
  bne x5, x0, L40
  jal x0, L41
L40:
  lui x6, %hi(_n)
  addi x6, x6, %lo(_n)
  addi x5, x0, 100
  sd x5, 0(x6)
L41:
  addi x7, x0, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  lui x6, %hi(L1)
  addi x6, x6, %lo(L1)
  addi x5, x0, 16
  sd x7, 0(x2)
  sd x6, 8(x2)
  sd x5, 16(x2)
  jal x1, _printString
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  addi x5, x0, 10
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _putchar
  addi x6, x8, 0
  addi x5, x0, -8
  add x6, x6, x5
  addi x5, x0, 0
  sd x5, 0(x6)
L42:
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  lui x5, %hi(_n)
  addi x5, x5, %lo(_n)
  ld x5, 0(x5)
  slt x5, x6, x5
  bne x5, x0, L43
  jal x0, L44
L43:
  lui x7, %hi(_arr)
  addi x7, x7, %lo(_arr)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x5, x6, x5
  add x6, x7, x5
  addi x5, x0, 238
  slli x5, x5, 8
  addi x5, x5, 238
  slli x5, x5, 8
  addi x5, x5, 238
  slli x5, x5, 8
  addi x5, x5, 238
  sd x5, 0(x2)
  jal x1, _getint
  ld x5, 0(x2)
  sd x5, 0(x6)
  addi x6, x8, 0
  addi x5, x0, -8
  add x7, x6, x5
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x5, x6, x5
  sd x5, 0(x7)
  jal x0, L42
L44:
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  addi x5, x0, 10
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _putchar
  lui x5, %hi(_n)
  addi x5, x5, %lo(_n)
  ld x6, 0(x5)
  addi x5, x0, 0
  slt x5, x5, x6
  bne x5, x0, L45
  jal x0, L46
L45:
  addi x9, x0, 238
  slli x9, x9, 8
  addi x9, x9, 238
  slli x9, x9, 8
  addi x9, x9, 238
  slli x9, x9, 8
  addi x9, x9, 238
  addi x7, x0, 0
  lui x5, %hi(_n)
  addi x5, x5, %lo(_n)
  ld x6, 0(x5)
  addi x5, x0, 1
  sub x5, x6, x5
  sd x9, 0(x2)
  sd x7, 8(x2)
  sd x5, 16(x2)
  jal x1, _mergeSort
L46:
  addi x7, x0, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  lui x6, %hi(L2)
  addi x6, x6, %lo(L2)
  addi x5, x0, 21
  sd x7, 0(x2)
  sd x6, 8(x2)
  sd x5, 16(x2)
  jal x1, _printString
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  addi x5, x0, 10
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _putchar
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  lui x5, %hi(_n)
  addi x5, x5, %lo(_n)
  ld x5, 0(x5)
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _printArray
  addi x5, x0, 0
  jal x0, L37
L37:
  sd x5, 0(x8)
  ld x9, 48(x2)
  ld x7, 40(x2)
  ld x6, 32(x2)
  ld x5, 24(x2)
  ld x1, 64(x2)
  ld x8, 56(x2)
  addi x2, x2, 80
  jalr x0, x1, 0

_putint:
  addi x2, x2, -16
  sd x10, 0(x2)
  sd x17, 8(x2)
  ld x10, 24(x2)
  addi x17, x0, 1
  ecall
  ld x17, 8(x2)
  ld x10, 0(x2)
  addi x2, x2, 16
  jalr x0, x1, 0

_putchar:
  addi x2, x2, -16
  sd x10, 0(x2)
  sd x17, 8(x2)
  ld x10, 24(x2)
  addi x17, x0, 11
  ecall
  ld x17, 8(x2)
  ld x10, 0(x2)
  addi x2, x2, 16
  jalr x0, x1, 0

_getint:
  addi x2, x2, -16
  sd x10, 0(x2)
  sd x17, 8(x2)
  addi x17, x0, 5
  ecall
  sd x10, 16(x2)
  ld x17, 8(x2)
  ld x10, 0(x2)
  addi x2, x2, 16
  jalr x0, x1, 0

_getchar:
  addi x2, x2, -16
  sd x10, 0(x2)
  sd x17, 8(x2)
  addi x17, x0, 12
  ecall
  sd x10, 16(x2)
  ld x17, 8(x2)
  ld x10, 0(x2)
  addi x2, x2, 16
  jalr x0, x1, 0

_new:
  addi x2, x2, -16
  sd x10, 0(x2)
  sd x17, 8(x2)
  ld x10, 24(x2)
  addi x17, x0, 9
  ecall
  sd x10, 16(x2)
  ld x17, 8(x2)
  ld x10, 0(x2)
  addi x2, x2, 16
  jalr x0, x1, 0

_del:
  jalr x0, x1, 0

_exit:
  addi x17, x0, 10
  ecall
