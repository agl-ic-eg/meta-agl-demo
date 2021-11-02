SUMMARY = "A SocketCAN over Ethernet tunnel"
HOMEPAGE = "https://github.com/mguentner/cannelloni"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://github.com/mguentner/cannelloni.git;protocol=https;branch=master \
           file://0001-Fix-compilation-on-latest-gcc-versions.patch \
           "

PV = "1.0.0+git${SRCPV}"
SRCREV = "aa0f0ecba2b8be0ea7ddd9952e719449a75330ec"

S = "${WORKDIR}/git"

inherit cmake
