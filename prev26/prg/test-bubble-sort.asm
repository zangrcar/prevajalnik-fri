.data
_n:
  .space 8
_i:
  .space 8
_j:
  .space 8
_tmp:
  .space 8
_arr:
  .space 800
_prompt_i:
  .space 8
L0:
  .byte 73, 110, 112, 117, 116, 32, 116, 104, 101, 32, 97, 114, 114, 97, 121, 32, 108, 101, 110, 103, 116, 104, 58
.align 3
L1:
  .byte 73, 110, 112, 117, 116, 32, 116, 104, 101, 32, 97, 114, 114, 97, 121, 58
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
  sd x5, 32(x2)
  sd x6, 40(x2)
  sd x7, 48(x2)
  sd x9, 56(x2)
  sd x8, 16(x2)
  sd x1, 24(x2)
  addi x8, x2, 80
  jal x0, L2
L2:
  addi x6, x8, 0
  addi x5, x0, -8
  add x6, x6, x5
  addi x5, x0, 0
  sd x5, 0(x6)
L4:
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x7, 0(x5)
  addi x6, x8, 0
  addi x5, x0, 16
  add x5, x6, x5
  ld x5, 0(x5)
  slt x5, x7, x5
  bne x5, x0, L5
  jal x0, L6
L5:
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
  jal x0, L4
L6:
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
  jal x0, L3
L3:
  sd x5, 0(x8)
  ld x9, 56(x2)
  ld x7, 48(x2)
  ld x6, 40(x2)
  ld x5, 32(x2)
  ld x1, 24(x2)
  ld x8, 16(x2)
  addi x2, x2, 80
  jalr x0, x1, 0

_main:
  addi x2, x2, -72
  sd x5, 40(x2)
  sd x6, 48(x2)
  sd x7, 56(x2)
  sd x9, 64(x2)
  sd x8, 24(x2)
  sd x1, 32(x2)
  addi x8, x2, 72
  jal x0, L7
L7:
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
  lui x6, %hi(_i)
  addi x6, x6, %lo(_i)
  addi x5, x0, 0
  sd x5, 0(x6)
L9:
  lui x5, %hi(_i)
  addi x5, x5, %lo(_i)
  ld x6, 0(x5)
  lui x5, %hi(_n)
  addi x5, x5, %lo(_n)
  ld x5, 0(x5)
  slt x5, x6, x5
  bne x5, x0, L10
  jal x0, L11
L10:
  lui x7, %hi(_arr)
  addi x7, x7, %lo(_arr)
  lui x5, %hi(_i)
  addi x5, x5, %lo(_i)
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
  lui x7, %hi(_i)
  addi x7, x7, %lo(_i)
  lui x5, %hi(_i)
  addi x5, x5, %lo(_i)
  ld x6, 0(x5)
  addi x5, x0, 1
  add x5, x6, x5
  sd x5, 0(x7)
  jal x0, L9
L11:
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
  lui x6, %hi(_i)
  addi x6, x6, %lo(_i)
  addi x5, x0, 0
  sd x5, 0(x6)
L17:
  lui x5, %hi(_i)
  addi x5, x5, %lo(_i)
  ld x6, 0(x5)
  lui x5, %hi(_n)
  addi x5, x5, %lo(_n)
  ld x5, 0(x5)
  slt x5, x6, x5
  bne x5, x0, L18
  jal x0, L19
L18:
  lui x6, %hi(_j)
  addi x6, x6, %lo(_j)
  addi x5, x0, 0
  sd x5, 0(x6)
L14:
  lui x5, %hi(_j)
  addi x5, x5, %lo(_j)
  ld x7, 0(x5)
  lui x5, %hi(_n)
  addi x5, x5, %lo(_n)
  ld x6, 0(x5)
  lui x5, %hi(_i)
  addi x5, x5, %lo(_i)
  ld x5, 0(x5)
  sub x6, x6, x5
  addi x5, x0, 1
  sub x5, x6, x5
  slt x5, x7, x5
  bne x5, x0, L15
  jal x0, L16
L15:
  lui x7, %hi(_arr)
  addi x7, x7, %lo(_arr)
  lui x5, %hi(_j)
  addi x5, x5, %lo(_j)
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x5, x6, x5
  add x5, x7, x5
  ld x9, 0(x5)
  lui x7, %hi(_arr)
  addi x7, x7, %lo(_arr)
  lui x5, %hi(_j)
  addi x5, x5, %lo(_j)
  ld x6, 0(x5)
  addi x5, x0, 1
  add x6, x6, x5
  addi x5, x0, 8
  mul x5, x6, x5
  add x5, x7, x5
  ld x5, 0(x5)
  slt x5, x5, x9
  bne x5, x0, L12
  jal x0, L13
L12:
  lui x9, %hi(_tmp)
  addi x9, x9, %lo(_tmp)
  lui x7, %hi(_arr)
  addi x7, x7, %lo(_arr)
  lui x5, %hi(_j)
  addi x5, x5, %lo(_j)
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x5, x6, x5
  add x5, x7, x5
  ld x5, 0(x5)
  sd x5, 0(x9)
  lui x7, %hi(_arr)
  addi x7, x7, %lo(_arr)
  lui x5, %hi(_j)
  addi x5, x5, %lo(_j)
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x5, x6, x5
  add x9, x7, x5
  lui x7, %hi(_arr)
  addi x7, x7, %lo(_arr)
  lui x5, %hi(_j)
  addi x5, x5, %lo(_j)
  ld x6, 0(x5)
  addi x5, x0, 1
  add x6, x6, x5
  addi x5, x0, 8
  mul x5, x6, x5
  add x5, x7, x5
  ld x5, 0(x5)
  sd x5, 0(x9)
  lui x7, %hi(_arr)
  addi x7, x7, %lo(_arr)
  lui x5, %hi(_j)
  addi x5, x5, %lo(_j)
  ld x6, 0(x5)
  addi x5, x0, 1
  add x6, x6, x5
  addi x5, x0, 8
  mul x5, x6, x5
  add x6, x7, x5
  lui x5, %hi(_tmp)
  addi x5, x5, %lo(_tmp)
  ld x5, 0(x5)
  sd x5, 0(x6)
L13:
  lui x7, %hi(_j)
  addi x7, x7, %lo(_j)
  lui x5, %hi(_j)
  addi x5, x5, %lo(_j)
  ld x6, 0(x5)
  addi x5, x0, 1
  add x5, x6, x5
  sd x5, 0(x7)
  jal x0, L14
L16:
  lui x7, %hi(_i)
  addi x7, x7, %lo(_i)
  lui x5, %hi(_i)
  addi x5, x5, %lo(_i)
  ld x6, 0(x5)
  addi x5, x0, 1
  add x5, x6, x5
  sd x5, 0(x7)
  jal x0, L17
L19:
  lui x6, %hi(_i)
  addi x6, x6, %lo(_i)
  addi x5, x0, 0
  sd x5, 0(x6)
L22:
  lui x5, %hi(_i)
  addi x5, x5, %lo(_i)
  ld x6, 0(x5)
  lui x5, %hi(_n)
  addi x5, x5, %lo(_n)
  ld x5, 0(x5)
  slt x5, x6, x5
  bne x5, x0, L23
  jal x0, L24
L23:
  addi x9, x0, 238
  slli x9, x9, 8
  addi x9, x9, 238
  slli x9, x9, 8
  addi x9, x9, 238
  slli x9, x9, 8
  addi x9, x9, 238
  lui x7, %hi(_arr)
  addi x7, x7, %lo(_arr)
  lui x5, %hi(_i)
  addi x5, x5, %lo(_i)
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x5, x6, x5
  add x5, x7, x5
  ld x5, 0(x5)
  sd x9, 0(x2)
  sd x5, 8(x2)
  jal x1, _putint
  lui x5, %hi(_i)
  addi x5, x5, %lo(_i)
  ld x7, 0(x5)
  lui x5, %hi(_n)
  addi x5, x5, %lo(_n)
  ld x6, 0(x5)
  addi x5, x0, 1
  sub x5, x6, x5
  slt x5, x7, x5
  bne x5, x0, L20
  jal x0, L21
L20:
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
L21:
  lui x7, %hi(_i)
  addi x7, x7, %lo(_i)
  lui x5, %hi(_i)
  addi x5, x5, %lo(_i)
  ld x6, 0(x5)
  addi x5, x0, 1
  add x5, x6, x5
  sd x5, 0(x7)
  jal x0, L22
L24:
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
  addi x5, x0, 0
  jal x0, L8
L8:
  sd x5, 0(x8)
  ld x9, 64(x2)
  ld x7, 56(x2)
  ld x6, 48(x2)
  ld x5, 40(x2)
  ld x1, 32(x2)
  ld x8, 24(x2)
  addi x2, x2, 72
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
