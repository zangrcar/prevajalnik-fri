.text
.globl main
main:
  addi x2, x2, -16
  sd x0, 0(x2)
  jal x1, _main
  addi x2, x2, 16
  addi x17, x0, 10
  ecall

_main:
  addi x2, x2, -72
  sd x4, 40(x2)
  sd x5, 48(x2)
  sd x6, 56(x2)
  sd x7, 64(x2)
  sd x8, 24(x2)
  sd x1, 32(x2)
  addi x8, x2, 72
  jal x0, L0
L0:
  addi x7, x0, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  addi x6, x0, 1
  addi x5, x0, 1
  addi x4, x0, 1
  sd x7, 0(x2)
  addi x10, x6, 0
  sd x5, 8(x2)
  sd x4, 16(x2)
  jal x1, _g
  ld x4, 0(x2)
  jal x0, L1
L1:
  sd x4, 0(x8)
  ld x7, 64(x2)
  ld x6, 56(x2)
  ld x5, 48(x2)
  ld x4, 40(x2)
  ld x1, 32(x2)
  ld x8, 24(x2)
  addi x2, x2, 72
  jalr x0, x1, 0

_g:
  addi x2, x2, -40
  sd x4, 16(x2)
  sd x5, 24(x2)
  sd x6, 32(x2)
  sd x8, 0(x2)
  sd x1, 8(x2)
  addi x8, x2, 40
  jal x0, L2
L2:
  addi x4, x10, 0
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x5, 0(x5)
  add x6, x4, x5
  addi x5, x8, 0
  addi x4, x0, 16
  add x4, x5, x4
  ld x4, 0(x4)
  add x4, x6, x4
  jal x0, L3
L3:
  sd x4, 0(x8)
  ld x6, 32(x2)
  ld x5, 24(x2)
  ld x4, 16(x2)
  ld x1, 8(x2)
  ld x8, 0(x2)
  addi x2, x2, 40
  jalr x0, x1, 0

_putint:
  addi x2, x2, -16
  sd x10, 0(x2)
  sd x17, 8(x2)
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
