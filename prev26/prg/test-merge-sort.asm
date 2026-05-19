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
  addi x2, x2, -104
  sd x5, 72(x2)
  sd x6, 80(x2)
  sd x8, 16(x2)
  sd x1, 24(x2)
  addi x8, x2, 104
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
  ld x5, 0(x5)
  sd x5, 32(x2)
  addi x6, x8, 0
  addi x5, x0, 16
  add x5, x6, x5
  ld x6, 0(x5)
  ld x5, 32(x2)
  slt x5, x5, x6
  bne x5, x0, L6
  jal x0, L7
L6:
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  sd x5, 40(x2)
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x5, 0(x5)
  sd x5, 48(x2)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  ld x5, 48(x2)
  add x5, x5, x6
  lbu x6, 0(x5)
  ld x5, 40(x2)
  sb x6, 0(x5)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  sd x5, 56(x2)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x6, x6, x5
  ld x5, 56(x2)
  sd x6, 0(x5)
  addi x5, x0, 238
  sd x5, 64(x2)
  ld x5, 64(x2)
  slli x5, x5, 8
  sd x5, 64(x2)
  ld x5, 64(x2)
  addi x5, x5, 238
  sd x5, 64(x2)
  ld x5, 64(x2)
  slli x5, x5, 8
  sd x5, 64(x2)
  ld x5, 64(x2)
  addi x5, x5, 238
  sd x5, 64(x2)
  ld x5, 64(x2)
  slli x5, x5, 8
  sd x5, 64(x2)
  ld x5, 64(x2)
  addi x5, x5, 238
  sd x5, 64(x2)
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  lbu x6, 0(x5)
  ld x5, 64(x2)
  sd x5, 0(x2)
  sd x6, 8(x2)
  jal x1, _putchar
  ld x5, 0(x2)
  jal x0, L5
L7:
  addi x5, x0, 0
  jal x0, L4
L4:
  sd x5, 0(x8)
  ld x6, 80(x2)
  ld x5, 72(x2)
  ld x1, 24(x2)
  ld x8, 16(x2)
  addi x2, x2, 104
  jalr x0, x1, 0

_printArray:
  addi x2, x2, -96
  sd x5, 72(x2)
  sd x6, 80(x2)
  sd x8, 16(x2)
  sd x1, 24(x2)
  addi x8, x2, 96
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
  ld x5, 0(x5)
  sd x5, 32(x2)
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x6, 0(x5)
  ld x5, 32(x2)
  slt x5, x5, x6
  bne x5, x0, L13
  jal x0, L14
L13:
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
  lui x5, %hi(_arr)
  sd x5, 48(x2)
  ld x5, 48(x2)
  addi x5, x5, %lo(_arr)
  sd x5, 48(x2)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x6, x6, x5
  ld x5, 48(x2)
  add x5, x5, x6
  ld x6, 0(x5)
  ld x5, 40(x2)
  sd x5, 0(x2)
  sd x6, 8(x2)
  jal x1, _putint
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x5, 0(x5)
  sd x5, 56(x2)
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  sub x6, x6, x5
  ld x5, 56(x2)
  slt x5, x5, x6
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
  add x5, x6, x5
  sd x5, 64(x2)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x6, x6, x5
  ld x5, 64(x2)
  sd x6, 0(x5)
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
  ld x6, 80(x2)
  ld x5, 72(x2)
  ld x1, 24(x2)
  ld x8, 16(x2)
  addi x2, x2, 96
  jalr x0, x1, 0

_merge:
  addi x2, x2, -344
  sd x5, 304(x2)
  sd x6, 312(x2)
  sd x8, 0(x2)
  sd x1, 8(x2)
  addi x8, x2, 344
  jal x0, L15
L15:
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  sd x5, 16(x2)
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x6, 0(x5)
  ld x5, 16(x2)
  sd x6, 0(x5)
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  sd x5, 24(x2)
  addi x6, x8, 0
  addi x5, x0, 16
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x6, x6, x5
  ld x5, 24(x2)
  sd x6, 0(x5)
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  sd x5, 32(x2)
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x6, 0(x5)
  ld x5, 32(x2)
  sd x6, 0(x5)
L20:
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x5, 0(x5)
  sd x5, 40(x2)
  addi x6, x8, 0
  addi x5, x0, 16
  add x5, x6, x5
  ld x6, 0(x5)
  ld x5, 40(x2)
  slt x5, x6, x5
  xori x5, x5, 1
  sd x5, 48(x2)
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  ld x5, 0(x5)
  sd x5, 56(x2)
  addi x6, x8, 0
  addi x5, x0, 24
  add x5, x6, x5
  ld x6, 0(x5)
  ld x5, 56(x2)
  slt x5, x6, x5
  xori x5, x5, 1
  ld x6, 48(x2)
  sltu x6, x0, x6
  sltu x5, x0, x5
  and x5, x6, x5
  bne x5, x0, L21
  jal x0, L22
L21:
  lui x5, %hi(_arr)
  sd x5, 64(x2)
  ld x5, 64(x2)
  addi x5, x5, %lo(_arr)
  sd x5, 64(x2)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x6, x6, x5
  ld x5, 64(x2)
  add x5, x5, x6
  ld x5, 0(x5)
  sd x5, 72(x2)
  lui x5, %hi(_arr)
  sd x5, 80(x2)
  ld x5, 80(x2)
  addi x5, x5, %lo(_arr)
  sd x5, 80(x2)
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x6, x6, x5
  ld x5, 80(x2)
  add x5, x5, x6
  ld x6, 0(x5)
  ld x5, 72(x2)
  slt x5, x6, x5
  xori x5, x5, 1
  bne x5, x0, L17
  jal x0, L18
L17:
  lui x5, %hi(_tmp)
  sd x5, 88(x2)
  ld x5, 88(x2)
  addi x5, x5, %lo(_tmp)
  sd x5, 88(x2)
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x6, x6, x5
  ld x5, 88(x2)
  add x5, x5, x6
  sd x5, 96(x2)
  lui x5, %hi(_arr)
  sd x5, 104(x2)
  ld x5, 104(x2)
  addi x5, x5, %lo(_arr)
  sd x5, 104(x2)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x6, x6, x5
  ld x5, 104(x2)
  add x5, x5, x6
  ld x6, 0(x5)
  ld x5, 96(x2)
  sd x6, 0(x5)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  sd x5, 112(x2)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x6, x6, x5
  ld x5, 112(x2)
  sd x6, 0(x5)
  jal x0, L19
L18:
  lui x5, %hi(_tmp)
  sd x5, 120(x2)
  ld x5, 120(x2)
  addi x5, x5, %lo(_tmp)
  sd x5, 120(x2)
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x6, x6, x5
  ld x5, 120(x2)
  add x5, x5, x6
  sd x5, 128(x2)
  lui x5, %hi(_arr)
  sd x5, 136(x2)
  ld x5, 136(x2)
  addi x5, x5, %lo(_arr)
  sd x5, 136(x2)
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x6, x6, x5
  ld x5, 136(x2)
  add x5, x5, x6
  ld x6, 0(x5)
  ld x5, 128(x2)
  sd x6, 0(x5)
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  sd x5, 144(x2)
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x6, x6, x5
  ld x5, 144(x2)
  sd x6, 0(x5)
L19:
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  sd x5, 152(x2)
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x6, x6, x5
  ld x5, 152(x2)
  sd x6, 0(x5)
  jal x0, L20
L22:
L23:
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x5, 0(x5)
  sd x5, 160(x2)
  addi x6, x8, 0
  addi x5, x0, 16
  add x5, x6, x5
  ld x6, 0(x5)
  ld x5, 160(x2)
  slt x5, x6, x5
  xori x5, x5, 1
  bne x5, x0, L24
  jal x0, L25
L24:
  lui x5, %hi(_tmp)
  sd x5, 168(x2)
  ld x5, 168(x2)
  addi x5, x5, %lo(_tmp)
  sd x5, 168(x2)
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x6, x6, x5
  ld x5, 168(x2)
  add x5, x5, x6
  sd x5, 176(x2)
  lui x5, %hi(_arr)
  sd x5, 184(x2)
  ld x5, 184(x2)
  addi x5, x5, %lo(_arr)
  sd x5, 184(x2)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x6, x6, x5
  ld x5, 184(x2)
  add x5, x5, x6
  ld x6, 0(x5)
  ld x5, 176(x2)
  sd x6, 0(x5)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  sd x5, 192(x2)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x6, x6, x5
  ld x5, 192(x2)
  sd x6, 0(x5)
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  sd x5, 200(x2)
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x6, x6, x5
  ld x5, 200(x2)
  sd x6, 0(x5)
  jal x0, L23
L25:
L26:
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  ld x5, 0(x5)
  sd x5, 208(x2)
  addi x6, x8, 0
  addi x5, x0, 24
  add x5, x6, x5
  ld x6, 0(x5)
  ld x5, 208(x2)
  slt x5, x6, x5
  xori x5, x5, 1
  bne x5, x0, L27
  jal x0, L28
L27:
  lui x5, %hi(_tmp)
  sd x5, 216(x2)
  ld x5, 216(x2)
  addi x5, x5, %lo(_tmp)
  sd x5, 216(x2)
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x6, x6, x5
  ld x5, 216(x2)
  add x5, x5, x6
  sd x5, 224(x2)
  lui x5, %hi(_arr)
  sd x5, 232(x2)
  ld x5, 232(x2)
  addi x5, x5, %lo(_arr)
  sd x5, 232(x2)
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x6, x6, x5
  ld x5, 232(x2)
  add x5, x5, x6
  ld x6, 0(x5)
  ld x5, 224(x2)
  sd x6, 0(x5)
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  sd x5, 240(x2)
  addi x6, x8, 0
  addi x5, x0, -16
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x6, x6, x5
  ld x5, 240(x2)
  sd x6, 0(x5)
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  sd x5, 248(x2)
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x6, x6, x5
  ld x5, 248(x2)
  sd x6, 0(x5)
  jal x0, L26
L28:
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  sd x5, 256(x2)
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x6, 0(x5)
  ld x5, 256(x2)
  sd x6, 0(x5)
L29:
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x5, 0(x5)
  sd x5, 264(x2)
  addi x6, x8, 0
  addi x5, x0, 24
  add x5, x6, x5
  ld x6, 0(x5)
  ld x5, 264(x2)
  slt x5, x6, x5
  xori x5, x5, 1
  bne x5, x0, L30
  jal x0, L31
L30:
  lui x5, %hi(_arr)
  sd x5, 272(x2)
  ld x5, 272(x2)
  addi x5, x5, %lo(_arr)
  sd x5, 272(x2)
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x6, x6, x5
  ld x5, 272(x2)
  add x5, x5, x6
  sd x5, 280(x2)
  lui x5, %hi(_tmp)
  sd x5, 288(x2)
  ld x5, 288(x2)
  addi x5, x5, %lo(_tmp)
  sd x5, 288(x2)
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x6, x6, x5
  ld x5, 288(x2)
  add x5, x5, x6
  ld x6, 0(x5)
  ld x5, 280(x2)
  sd x6, 0(x5)
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  sd x5, 296(x2)
  addi x6, x8, 0
  addi x5, x0, -24
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x6, x6, x5
  ld x5, 296(x2)
  sd x6, 0(x5)
  jal x0, L29
L31:
  addi x5, x0, 0
  jal x0, L16
L16:
  sd x5, 0(x8)
  ld x6, 312(x2)
  ld x5, 304(x2)
  ld x1, 8(x2)
  ld x8, 0(x2)
  addi x2, x2, 344
  jalr x0, x1, 0

_mergeSort:
  addi x2, x2, -152
  sd x5, 128(x2)
  sd x6, 136(x2)
  sd x8, 32(x2)
  sd x1, 40(x2)
  addi x8, x2, 152
  jal x0, L32
L32:
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x5, 0(x5)
  sd x5, 48(x2)
  addi x6, x8, 0
  addi x5, x0, 16
  add x5, x6, x5
  ld x6, 0(x5)
  ld x5, 48(x2)
  slt x5, x5, x6
  bne x5, x0, L34
  jal x0, L35
L34:
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  sd x5, 56(x2)
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x5, 0(x5)
  sd x5, 64(x2)
  addi x6, x8, 0
  addi x5, x0, 16
  add x5, x6, x5
  ld x6, 0(x5)
  ld x5, 64(x2)
  add x6, x5, x6
  addi x5, x0, 2
  div x6, x6, x5
  ld x5, 56(x2)
  sd x6, 0(x5)
  addi x5, x0, 238
  sd x5, 72(x2)
  ld x5, 72(x2)
  slli x5, x5, 8
  sd x5, 72(x2)
  ld x5, 72(x2)
  addi x5, x5, 238
  sd x5, 72(x2)
  ld x5, 72(x2)
  slli x5, x5, 8
  sd x5, 72(x2)
  ld x5, 72(x2)
  addi x5, x5, 238
  sd x5, 72(x2)
  ld x5, 72(x2)
  slli x5, x5, 8
  sd x5, 72(x2)
  ld x5, 72(x2)
  addi x5, x5, 238
  sd x5, 72(x2)
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x5, 0(x5)
  sd x5, 80(x2)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  ld x5, 72(x2)
  sd x5, 0(x2)
  ld x5, 80(x2)
  sd x5, 8(x2)
  sd x6, 16(x2)
  jal x1, _mergeSort
  addi x5, x0, 238
  sd x5, 88(x2)
  ld x5, 88(x2)
  slli x5, x5, 8
  sd x5, 88(x2)
  ld x5, 88(x2)
  addi x5, x5, 238
  sd x5, 88(x2)
  ld x5, 88(x2)
  slli x5, x5, 8
  sd x5, 88(x2)
  ld x5, 88(x2)
  addi x5, x5, 238
  sd x5, 88(x2)
  ld x5, 88(x2)
  slli x5, x5, 8
  sd x5, 88(x2)
  ld x5, 88(x2)
  addi x5, x5, 238
  sd x5, 88(x2)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x5, x6, x5
  sd x5, 96(x2)
  addi x6, x8, 0
  addi x5, x0, 16
  add x5, x6, x5
  ld x6, 0(x5)
  ld x5, 88(x2)
  sd x5, 0(x2)
  ld x5, 96(x2)
  sd x5, 8(x2)
  sd x6, 16(x2)
  jal x1, _mergeSort
  addi x5, x0, 238
  sd x5, 104(x2)
  ld x5, 104(x2)
  slli x5, x5, 8
  sd x5, 104(x2)
  ld x5, 104(x2)
  addi x5, x5, 238
  sd x5, 104(x2)
  ld x5, 104(x2)
  slli x5, x5, 8
  sd x5, 104(x2)
  ld x5, 104(x2)
  addi x5, x5, 238
  sd x5, 104(x2)
  ld x5, 104(x2)
  slli x5, x5, 8
  sd x5, 104(x2)
  ld x5, 104(x2)
  addi x5, x5, 238
  sd x5, 104(x2)
  addi x6, x8, 0
  addi x5, x0, 8
  add x5, x6, x5
  ld x5, 0(x5)
  sd x5, 112(x2)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x5, 0(x5)
  sd x5, 120(x2)
  addi x6, x8, 0
  addi x5, x0, 16
  add x5, x6, x5
  ld x6, 0(x5)
  ld x5, 104(x2)
  sd x5, 0(x2)
  ld x5, 112(x2)
  sd x5, 8(x2)
  ld x5, 120(x2)
  sd x5, 16(x2)
  sd x6, 24(x2)
  jal x1, _merge
  ld x5, 0(x2)
L35:
  addi x5, x0, 0
  jal x0, L33
L33:
  sd x5, 0(x8)
  ld x6, 136(x2)
  ld x5, 128(x2)
  ld x1, 40(x2)
  ld x8, 32(x2)
  addi x2, x2, 152
  jalr x0, x1, 0

_main:
  addi x2, x2, -144
  sd x5, 120(x2)
  sd x6, 128(x2)
  sd x8, 24(x2)
  sd x1, 32(x2)
  addi x8, x2, 144
  jal x0, L36
L36:
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
  lui x5, %hi(L0)
  sd x5, 96(x2)
  ld x5, 96(x2)
  addi x5, x5, %lo(L0)
  sd x5, 96(x2)
  addi x6, x0, 23
  ld x5, 40(x2)
  sd x5, 0(x2)
  ld x5, 96(x2)
  sd x5, 8(x2)
  sd x6, 16(x2)
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
  addi x5, x0, 238
  sd x5, 48(x2)
  ld x5, 48(x2)
  slli x5, x5, 8
  sd x5, 48(x2)
  ld x5, 48(x2)
  addi x5, x5, 238
  sd x5, 48(x2)
  ld x5, 48(x2)
  slli x5, x5, 8
  sd x5, 48(x2)
  ld x5, 48(x2)
  addi x5, x5, 238
  sd x5, 48(x2)
  ld x5, 48(x2)
  slli x5, x5, 8
  sd x5, 48(x2)
  ld x5, 48(x2)
  addi x5, x5, 238
  sd x5, 48(x2)
  lui x5, %hi(L1)
  sd x5, 104(x2)
  ld x5, 104(x2)
  addi x5, x5, %lo(L1)
  sd x5, 104(x2)
  addi x6, x0, 16
  ld x5, 48(x2)
  sd x5, 0(x2)
  ld x5, 104(x2)
  sd x5, 8(x2)
  sd x6, 16(x2)
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
  lui x5, %hi(_arr)
  sd x5, 56(x2)
  ld x5, 56(x2)
  addi x5, x5, %lo(_arr)
  sd x5, 56(x2)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 8
  mul x6, x6, x5
  ld x5, 56(x2)
  add x6, x5, x6
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
  add x5, x6, x5
  sd x5, 64(x2)
  addi x6, x8, 0
  addi x5, x0, -8
  add x5, x6, x5
  ld x6, 0(x5)
  addi x5, x0, 1
  add x6, x6, x5
  ld x5, 64(x2)
  sd x6, 0(x5)
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
  addi x5, x0, 238
  sd x5, 72(x2)
  ld x5, 72(x2)
  slli x5, x5, 8
  sd x5, 72(x2)
  ld x5, 72(x2)
  addi x5, x5, 238
  sd x5, 72(x2)
  ld x5, 72(x2)
  slli x5, x5, 8
  sd x5, 72(x2)
  ld x5, 72(x2)
  addi x5, x5, 238
  sd x5, 72(x2)
  ld x5, 72(x2)
  slli x5, x5, 8
  sd x5, 72(x2)
  ld x5, 72(x2)
  addi x5, x5, 238
  sd x5, 72(x2)
  addi x5, x0, 0
  sd x5, 80(x2)
  lui x5, %hi(_n)
  addi x5, x5, %lo(_n)
  ld x6, 0(x5)
  addi x5, x0, 1
  sub x6, x6, x5
  ld x5, 72(x2)
  sd x5, 0(x2)
  ld x5, 80(x2)
  sd x5, 8(x2)
  sd x6, 16(x2)
  jal x1, _mergeSort
L46:
  addi x5, x0, 238
  sd x5, 88(x2)
  ld x5, 88(x2)
  slli x5, x5, 8
  sd x5, 88(x2)
  ld x5, 88(x2)
  addi x5, x5, 238
  sd x5, 88(x2)
  ld x5, 88(x2)
  slli x5, x5, 8
  sd x5, 88(x2)
  ld x5, 88(x2)
  addi x5, x5, 238
  sd x5, 88(x2)
  ld x5, 88(x2)
  slli x5, x5, 8
  sd x5, 88(x2)
  ld x5, 88(x2)
  addi x5, x5, 238
  sd x5, 88(x2)
  lui x5, %hi(L2)
  sd x5, 112(x2)
  ld x5, 112(x2)
  addi x5, x5, %lo(L2)
  sd x5, 112(x2)
  addi x6, x0, 21
  ld x5, 88(x2)
  sd x5, 0(x2)
  ld x5, 112(x2)
  sd x5, 8(x2)
  sd x6, 16(x2)
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
  ld x6, 128(x2)
  ld x5, 120(x2)
  ld x1, 32(x2)
  ld x8, 24(x2)
  addi x2, x2, 144
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
