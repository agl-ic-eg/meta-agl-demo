SUMMARY = "Music data for AGL demo"
DESCRIPTION = "Music data for AGL demo aims to setup pre-install music data to use AGL demo use.  All music data approved redistribution by provider. "
HOMEPAGE = "https://github.com/agl-ic-eg/demo-music-cache"
SECTION = "Multimedia"
LICENSE = "CC-BY-2.1-JP"
LIC_FILES_CHKSUM = "file://andotowa.quu.cc/LICENSE;md5=f2eaecf5559b657628481f004767cf27"

SRC_URI = " \
    git://github.com/agl-ic-eg/demo-music-cache.git;branch=master;protocol=https \
"
SRCREV = "c300ede365fc64ea759ca696fea676069ef80e01"

inherit allarch

S = "${WORKDIR}/git"

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
ERROR_QA:remove = "empty-dirs"

do_compile[noexec] = "1"
do_install() {
    install -d ${D}/media/pre-install/andotowa.quu.cc
    cp ${S}/andotowa.quu.cc/* ${D}/media/pre-install/andotowa.quu.cc/
}

PACKAGES = "\
    ${PN} \
"

FILES:${PN} = " \
    /media/pre-install/* \
"
