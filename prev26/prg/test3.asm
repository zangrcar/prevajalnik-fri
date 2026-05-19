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

_main:
  addi x2, x2, -64
  sd x5, 32(x2)
  sd x6, 40(x2)
  sd x7, 48(x2)
  sd x8, 16(x2)
  sd x1, 24(x2)
  addi x8, x2, 64
  jal x0, L1
L1:
  addi x6, x8, 0
  addi x5, x0, -8
  add x7, x6, x5
  addi x6, x8, 0
  addi x5, x0, 6
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, L0
  ld x5, 0(x2)
  sd x5, 0(x7)
  addi x7, x0, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x5, 0(x5)
  sd x7, 0(x2)
  sd x5, 8(x2)
  jal x1, _putint
  ld x5, 0(x2)
  addi x5, x0, 0
  jal x0, L2
L2:
  sd x5, 0(x8)
  ld x7, 48(x2)
  ld x6, 40(x2)
  ld x5, 32(x2)
  ld x1, 24(x2)
  ld x8, 16(x2)
  addi x2, x2, 64
  jalr x0, x1, 0

L0:
  addi x2, x2, -32
  sd x5, 16(x2)
  sd x6, 24(x2)
  sd x8, 0(x2)
  sd x1, 8(x2)
  addi x8, x2, 32
  jal x0, L3
L3:
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x5, x6, x5
  jal x0, L4
L4:
  sd x5, 0(x8)
  ld x6, 24(x2)
  ld x5, 16(x2)
  ld x1, 8(x2)
  ld x8, 0(x2)
  addi x2, x2, 32
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
