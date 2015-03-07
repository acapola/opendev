cmd_/media/sf_dev/linux_rnd_driver/../opendev/random_c/random.o :=  gcc-4.6 -Wp,-MD,/media/sf_dev/linux_rnd_driver/../opendev/random_c/.random.o.d  -nostdinc -isystem /usr/lib/gcc/x86_64-linux-gnu/4.6/include -I/usr/src/linux-headers-3.2.0-4-common/arch/x86/include -Iarch/x86/include/generated -Iinclude  -I/usr/src/linux-headers-3.2.0-4-common/include -include /usr/src/linux-headers-3.2.0-4-common/include/linux/kconfig.h   -I/media/sf_dev/linux_rnd_driver -D__KERNEL__ -Wall -Wundef -Wstrict-prototypes -Wno-trigraphs -fno-strict-aliasing -fno-common -Werror-implicit-function-declaration -Wno-format-security -fno-delete-null-pointer-checks -Os -m64 -mtune=generic -mno-red-zone -mcmodel=kernel -funit-at-a-time -maccumulate-outgoing-args -fstack-protector -DCONFIG_AS_CFI=1 -DCONFIG_AS_CFI_SIGNAL_FRAME=1 -DCONFIG_AS_CFI_SECTIONS=1 -DCONFIG_AS_FXSAVEQ=1 -pipe -Wno-sign-compare -fno-asynchronous-unwind-tables -mno-sse -mno-mmx -mno-sse2 -mno-3dnow -Wframe-larger-than=2048 -Wno-unused-but-set-variable -fomit-frame-pointer -g -Wdeclaration-after-statement -Wno-pointer-sign -fno-strict-overflow -fconserve-stack -DCC_HAVE_ASM_GOTO  -DMODULE  -D"KBUILD_STR(s)=\#s" -D"KBUILD_BASENAME=KBUILD_STR(random)"  -D"KBUILD_MODNAME=KBUILD_STR(kidekin_rng)" -c -o /media/sf_dev/linux_rnd_driver/../opendev/random_c/.tmp_random.o /media/sf_dev/linux_rnd_driver/../opendev/random_c/random.c

source_/media/sf_dev/linux_rnd_driver/../opendev/random_c/random.o := /media/sf_dev/linux_rnd_driver/../opendev/random_c/random.c

deps_/media/sf_dev/linux_rnd_driver/../opendev/random_c/random.o := \
  /media/sf_dev/linux_rnd_driver/../opendev/random_c/random.h \

/media/sf_dev/linux_rnd_driver/../opendev/random_c/random.o: $(deps_/media/sf_dev/linux_rnd_driver/../opendev/random_c/random.o)

$(deps_/media/sf_dev/linux_rnd_driver/../opendev/random_c/random.o):
