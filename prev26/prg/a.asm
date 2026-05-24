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
  addi x2, x2, -88
  sd x5, 48(x2)
  sd x6, 56(x2)
  sd x7, 64(x2)
  sd x9, 72(x2)
  sd x10, 80(x2)
  sd x8, 32(x2)
  sd x1, 40(x2)
  addi x8, x2, 88
  jal x0, L0
L0:
  addi x10, x0, 238
  slli x10, x10, 8
  addi x10, x10, 238
  slli x10, x10, 8
  addi x10, x10, 238
  slli x10, x10, 8
  addi x10, x10, 238
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  addi x5, x0, 2
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _p
  ld x7, 0(x2)
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  addi x5, x0, 1
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _p
  ld x6, 0(x2)
  addi x9, x0, 238
  slli x9, x9, 8
  addi x9, x9, 238
  slli x9, x9, 8
  addi x9, x9, 238
  slli x9, x9, 8
  addi x9, x9, 238
  addi x5, x0, 3
  sd x9, 0(x2)
  sd x5, 8(x2)
  jal x1, _p
  ld x5, 0(x2)
  sd x10, 0(x2)
  sd x6, 8(x2)
  sd x7, 16(x2)
  sd x5, 24(x2)
  jal x1, _g
  addi x5, x0, 238
  slli x5, x5, 8
  addi x5, x5, 238
  slli x5, x5, 8
  addi x5, x5, 238
  slli x5, x5, 8
  addi x5, x5, 238
  sd x5, 0(x2)
  jal x1, _h
  ld x5, 0(x2)
  jal x0, L1
L1:
  sd x5, 0(x8)
  ld x10, 80(x2)
  ld x9, 72(x2)
  ld x7, 64(x2)
  ld x6, 56(x2)
  ld x5, 48(x2)
  ld x1, 40(x2)
  ld x8, 32(x2)
  addi x2, x2, 88
  jalr x0, x1, 0

_g:
  addi x2, x2, -40
  sd x5, 16(x2)
  sd x6, 24(x2)
  sd x7, 32(x2)
  sd x8, 0(x2)
  sd x1, 8(x2)
  addi x8, x2, 40
  jal x0, L2
L2:
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x7, 0(x5)
  addi x6, x8, 0
  addi x5, x0, 16
  add x5, x6, x5
  ld x5, 0(x5)
  add x7, x7, x5
  addi x6, x8, 0
  addi x5, x0, 24
  add x5, x6, x5
  ld x5, 0(x5)
  add x5, x7, x5
  jal x0, L3
L3:
  sd x5, 0(x8)
  ld x7, 32(x2)
  ld x6, 24(x2)
  ld x5, 16(x2)
  ld x1, 8(x2)
  ld x8, 0(x2)
  addi x2, x2, 40
  jalr x0, x1, 0

_p:
  addi x2, x2, -56
  sd x5, 32(x2)
  sd x6, 40(x2)
  sd x7, 48(x2)
  sd x8, 16(x2)
  sd x1, 24(x2)
  addi x8, x2, 56
  jal x0, L4
L4:
  addi x7, x0, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  slli x7, x7, 8
  addi x7, x7, 238
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x5, 0(x5)
  sd x7, 0(x2)
  sd x5, 8(x2)
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
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x5, 0(x5)
  jal x0, L5
L5:
  sd x5, 0(x8)
  ld x7, 48(x2)
  ld x6, 40(x2)
  ld x5, 32(x2)
  ld x1, 24(x2)
  ld x8, 16(x2)
  addi x2, x2, 56
  jalr x0, x1, 0

_h:
  addi x2, x2, -24
  sd x5, 16(x2)
  sd x8, 0(x2)
  sd x1, 8(x2)
  addi x8, x2, 24
  jal x0, L6
L6:
  addi x5, x0, 0
  jal x0, L7
L7:
  sd x5, 0(x8)
  ld x5, 16(x2)
  ld x1, 8(x2)
  ld x8, 0(x2)
  addi x2, x2, 24
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
