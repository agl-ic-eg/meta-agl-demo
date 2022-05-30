DESCRIPTION = "slLIN driver module"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://sllin.c;beginline=7;endline=37;md5=6408e14dba951f8cbe3c2a003a0d89d2"

inherit module

DEPENDS = "virtual/kernel"

SRC_URI = "git://github.com/lin-bus/linux-lin.git;protocol=https;branch=master \
           file://0001-update-makefile.patch;pnum=2 \
           file://0002-5.4-less-than-110-fix.patch;pnum=2 \
"
SRCREV = "beb057d7505e0c4d7c61f3f4927b76916ec00e88"
S = "${WORKDIR}/git/sllin"

PV = "0.1+git${SRCPV}"

KERNEL_MODULE_AUTOLOAD:append = " sllin"
KERNEL_MODULE_PROBECONF:append = " sllin"

SLLINBAUDRATE ??= "9600"
module_conf_sllin = "options sllin baudrate=${SLLINBAUDRATE}"
