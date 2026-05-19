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
  addi x2, x2, -80
  sd x5, 48(x2)
  sd x6, 56(x2)
  sd x8, 16(x2)
  sd x1, 24(x2)
  addi x8, x2, 80
  jal x0, L0
L0:
  addi x6, x8, 0
  addi x5, x0, -16
  add x6, x6, x5
  addi x5, x0, 42
  sd x5, 0(x6)
  addi x6, x8, 0
  addi x5, x0, -16
  add x6, x6, x5
  addi x5, x0, 8
  add x6, x6, x5
  addi x5, x0, 65
  sb x5, 0(x6)
  addi x5, x0, 238
  sd x5, 32(x2)
  ld x5, 32(x2)
  slli x5, x5, 8
  sd x5, 32(x2)
  ld x5, 32(x2)
  addi x5, x5, 238
  sd x5, 32(x2)
  ld x5, 32(x2)
  slli x5, x5, 8
  sd x5, 32(x2)
  ld x5, 32(x2)
  addi x5, x5, 238
  sd x5, 32(x2)
  ld x5, 32(x2)
  slli x5, x5, 8
  sd x5, 32(x2)
  ld x5, 32(x2)
  addi x5, x5, 238
  sd x5, 32(x2)
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  ld x6, 0(x5)
  ld x5, 32(x2)
  sd x5, 0(x2)
  sd x6, 8(x2)
  jal x1, _putint
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
  addi x5, x0, 238
  sd x5, 40(x2)
  ld x5, 40(x2)
  slli x5, x5, 8
  sd x5, 40(x2)
  ld x5, 40(x2)
  addi x5, x5, 238
  sd x5, 40(x2)
  ld x5, 40(x2)
  slli x5, x5, 8
  sd x5, 40(x2)
  ld x5, 40(x2)
  addi x5, x5, 238
  sd x5, 40(x2)
  ld x5, 40(x2)
  slli x5, x5, 8
  sd x5, 40(x2)
  ld x5, 40(x2)
  addi x5, x5, 238
  sd x5, 40(x2)
  addi x6, x8, 0
  addi x5, x0, -16
  add x6, x6, x5
  addi x5, x0, 8
  add x5, x6, x5
  lbu x6, 0(x5)
  ld x5, 40(x2)
  sd x5, 0(x2)
  sd x6, 8(x2)
  jal x1, _putchar
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
  jal x0, L1
L1:
  sd x5, 0(x8)
  ld x6, 56(x2)
  ld x5, 48(x2)
  ld x1, 24(x2)
  ld x8, 16(x2)
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
