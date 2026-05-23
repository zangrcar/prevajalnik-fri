.text
.globl main
main:
  addi x2, x2, -16
  addi x10, x0, 0
  jal x1, _main
  addi x2, x2, 16
  addi x17, x0, 10
  ecall

_main:
  addi x2, x2, -72
  sd x5, 24(x2)
  sd x6, 32(x2)
  sd x7, 40(x2)
  sd x8, 8(x2)
  sd x1, 16(x2)
  addi x8, x2, 72
  sd x10, -8(x8)
  jal x0, L3
L3:
  addi x6, x8, 0
  addi x5, x0, -16
  add x6, x6, x5
  addi x5, x0, 10
  sd x5, 0(x6)
  addi x6, x8, 0
  addi x5, x0, -24
  add x7, x6, x5
  addi x6, x8, 0
  addi x5, x0, 3
  sd x5, 0(x2)
  addi x10, x6, 0
  jal x1, L0
  addi x5, x10, 0
  sd x5, 0(x7)
  addi x7, x0, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x5, 0(x5)
  sd x5, 0(x2)
  addi x10, x7, 0
  jal x1, _putint
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  addi x5, x0, 10
  sd x5, 0(x2)
  addi x10, x6, 0
  jal x1, _putchar
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x5, 0(x5)
  jal x0, L4
L4:
  addi x10, x5, 0
  ld x7, 40(x2)
  ld x6, 32(x2)
  ld x5, 24(x2)
  ld x1, 16(x2)
  ld x8, 8(x2)
  addi x2, x2, 72
  jalr x0, x1, 0

L0:
  addi x2, x2, -56
  sd x5, 24(x2)
  sd x6, 32(x2)
  sd x8, 8(x2)
  sd x1, 16(x2)
  addi x8, x2, 56
  sd x10, -8(x8)
  jal x0, L5
L5:
  addi x6, x8, 0
  addi x5, x0, -16
  add x6, x6, x5
  addi x5, x0, 7
  sd x5, 0(x6)
  addi x6, x8, 0
  addi x5, x0, 4
  sd x5, 0(x2)
  addi x10, x6, 0
  jal x1, L2
  addi x5, x10, 0
  jal x0, L6
L6:
  addi x10, x5, 0
  ld x6, 32(x2)
  ld x5, 24(x2)
  ld x1, 16(x2)
  ld x8, 8(x2)
  addi x2, x2, 56
  jalr x0, x1, 0

L1:
  addi x2, x2, -48
  sd x5, 16(x2)
  sd x6, 24(x2)
  sd x7, 32(x2)
  sd x8, 0(x2)
  sd x1, 8(x2)
  addi x8, x2, 48
  sd x10, -8(x8)
  jal x0, L7
L7:
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, -16
  add x5, x6, x5
  ld x7, 0(x5)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x5, 0(x5)
  ld x5, 0(x5)
  add x7, x7, x5
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, -16
  add x5, x6, x5
  ld x5, 0(x5)
  add x6, x7, x5
  ld x5, 0(x8)
  add x5, x6, x5
  jal x0, L8
L8:
  addi x10, x5, 0
  ld x7, 32(x2)
  ld x6, 24(x2)
  ld x5, 16(x2)
  ld x1, 8(x2)
  ld x8, 0(x2)
  addi x2, x2, 48
  jalr x0, x1, 0

L2:
  addi x2, x2, -64
  sd x5, 24(x2)
  sd x6, 32(x2)
  sd x7, 40(x2)
  sd x8, 8(x2)
  sd x1, 16(x2)
  addi x8, x2, 64
  sd x10, -8(x8)
  jal x0, L9
L9:
  addi x6, x8, 0
  addi x5, x0, -16
  add x7, x6, x5
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  ld x5, 0(x8)
  sd x5, 0(x2)
  addi x10, x6, 0
  jal x1, L1
  addi x5, x10, 0
  sd x5, 0(x7)
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  ld x7, 0(x5)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, -16
  add x5, x6, x5
  ld x5, 0(x5)
  add x7, x7, x5
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x5, 0(x5)
  ld x5, 0(x5)
  add x7, x7, x5
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, -16
  add x5, x6, x5
  ld x5, 0(x5)
  add x5, x7, x5
  jal x0, L10
L10:
  addi x10, x5, 0
  ld x7, 40(x2)
  ld x6, 32(x2)
  ld x5, 24(x2)
  ld x1, 16(x2)
  ld x8, 8(x2)
  addi x2, x2, 64
  jalr x0, x1, 0

_putint:
  addi x2, x2, -16
  sd x17, 8(x2)
  ld x10, 16(x2)
  addi x17, x0, 1
  ecall
  ld x17, 8(x2)
  addi x2, x2, 16
  jalr x0, x1, 0

_putchar:
  addi x2, x2, -16
  sd x17, 8(x2)
  ld x10, 16(x2)
  addi x17, x0, 11
  ecall
  ld x17, 8(x2)
  addi x2, x2, 16
  jalr x0, x1, 0

_getint:
  addi x2, x2, -16
  sd x17, 8(x2)
  addi x17, x0, 5
  ecall
  ld x17, 8(x2)
  addi x2, x2, 16
  jalr x0, x1, 0

_getchar:
  addi x2, x2, -16
  sd x17, 8(x2)
  addi x17, x0, 12
  ecall
  ld x17, 8(x2)
  addi x2, x2, 16
  jalr x0, x1, 0

_new:
  addi x2, x2, -16
  sd x17, 8(x2)
  ld x10, 16(x2)
  addi x17, x0, 9
  ecall
  ld x17, 8(x2)
  addi x2, x2, 16
  jalr x0, x1, 0

_del:
  jalr x0, x1, 0

_exit:
  addi x17, x0, 10
  ecall
