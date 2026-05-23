.data
_i:
  .space 8

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
  sd x5, 32(x2)
  sd x6, 40(x2)
  sd x7, 48(x2)
  sd x8, 16(x2)
  sd x1, 24(x2)
  addi x8, x2, 56
  jal x0, L0
L0:
  lui x6, %hi(_i)
  addi x6, x6, %lo(_i)
  addi x5, x0, 0
  sd x5, 0(x6)
L2:
  lui x5, %hi(_i)
  addi x5, x5, %lo(_i)
  ld x6, 0(x5)
  addi x5, x0, 10
  xor x5, x6, x5
  sltiu x5, x5, 1
  bne x5, x0, L4
  jal x0, L3
L3:
  lui x7, %hi(_i)
  addi x7, x7, %lo(_i)
  lui x5, %hi(_i)
  addi x5, x5, %lo(_i)
  ld x6, 0(x5)
  addi x5, x0, 1
  add x5, x6, x5
  sd x5, 0(x7)
  jal x0, L2
L4:
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  lui x5, %hi(_i)
  addi x5, x5, %lo(_i)
  ld x5, 0(x5)
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _putint
  addi x5, x0, 0
  jal x0, L1
L1:
  sd x5, 0(x8)
  ld x7, 48(x2)
  ld x6, 40(x2)
  ld x5, 32(x2)
  ld x1, 24(x2)
  ld x8, 16(x2)
  addi x2, x2, 56
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
