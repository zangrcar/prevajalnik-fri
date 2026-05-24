.data
_a:
  .space 8
__main:
    .dword 0
__g:
    .dword 0

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
la x30, __main
ld x31, 0(x30)
addi x31, x31, 1
sd, x31, 0(x30)
addi x30, x0, 10
ble x31, x30, _s__main
jal _exit
_s__main:
  addi x2, x2, -48
  sd x5, 32(x2)
  sd x6, 40(x2)
  sd x8, 16(x2)
  sd x1, 24(x2)
  addi x8, x2, 48
  jal x0, L0
L0:
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  addi x5, x0, 8
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _g
  addi x6, x0, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  slli x6, x6, 8
  addi x6, x6, 238
  addi x5, x0, 20
  sd x6, 0(x2)
  sd x5, 8(x2)
  jal x1, _g
  ld x5, 0(x2)
  jal x0, L1
L1:
la x30, __main
ld x31, 0(x30)
addi x31, x31, -1
sd, x31, 0(x30)
  sd x5, 0(x8)
  ld x6, 40(x2)
  ld x5, 32(x2)
  ld x1, 24(x2)
  ld x8, 16(x2)
  addi x2, x2, 48
  jalr x0, x1, 0

_g:
la x30, __g
ld x31, 0(x30)
addi x31, x31, 1
sd, x31, 0(x30)
addi x30, x0, 10
ble x31, x30, _s__g
jal _exit
_s__g:
  addi x2, x2, -56
  sd x5, 32(x2)
  sd x6, 40(x2)
  sd x7, 48(x2)
  sd x8, 16(x2)
  sd x1, 24(x2)
  addi x8, x2, 56
  jal x0, L2
L2:
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
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 0
  slt x5, x5, x6
  bne x5, x0, L4
  jal x0, L5
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
  ld x6, 0(x5)
  addi x5, x0, 1
  sub x5, x6, x5
  sd x7, 0(x2)
  sd x5, 8(x2)
  jal x1, _g
L5:
  addi x5, x0, 0
  jal x0, L3
L3:
la x30, __g
ld x31, 0(x30)
addi x31, x31, -1
sd, x31, 0(x30)
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
