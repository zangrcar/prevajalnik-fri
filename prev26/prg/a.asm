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
  addi x2, x2, -56
  sd x5, 24(x2)
  sd x6, 32(x2)
  sd x8, 8(x2)
  sd x1, 16(x2)
  addi x8, x2, 56
  jal x0, L2
L2:
  addi x5, x8, 0
  sd x5, 0(x2)
  jal x1, L0
  addi x6, x8, 0
  addi x5, x0, -8
  add x6, x6, x5
  addi x5, x0, 4
  sd x5, 0(x6)
  addi x6, x8, 0
  addi x5, x0, -16
  add x6, x6, x5
  addi x5, x8, 0
  sd x5, 0(x2)
  jal x1, L0
  ld x5, 0(x2)
  sd x5, 0(x6)
  addi x5, x8, 0
  sd x5, 0(x2)
  jal x1, L1
  addi x5, x0, 0
  jal x0, L3
L3:
  sd x5, 0(x8)
  ld x6, 32(x2)
  ld x5, 24(x2)
  ld x1, 16(x2)
  ld x8, 8(x2)
  addi x2, x2, 56
  jalr x0, x1, 0

L0:
  addi x2, x2, -24
  sd x5, 16(x2)
  sd x8, 0(x2)
  sd x1, 8(x2)
  addi x8, x2, 24
  jal x0, L4
L4:
  addi x5, x0, 3
  jal x0, L5
L5:
  sd x5, 0(x8)
  ld x5, 16(x2)
  ld x1, 8(x2)
  ld x8, 0(x2)
  addi x2, x2, 24
  jalr x0, x1, 0

L1:
  addi x2, x2, -40
  sd x5, 16(x2)
  sd x6, 24(x2)
  sd x8, 0(x2)
  sd x1, 8(x2)
  addi x8, x2, 40
  jal x0, L6
L6:
  addi x6, x8, 0
  addi x5, x0, -8
  add x6, x6, x5
  addi x5, x0, 4
  sd x5, 0(x6)
  addi x5, x0, 0
  jal x0, L7
L7:
  sd x5, 0(x8)
  ld x6, 24(x2)
  ld x5, 16(x2)
  ld x1, 8(x2)
  ld x8, 0(x2)
  addi x2, x2, 40
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
