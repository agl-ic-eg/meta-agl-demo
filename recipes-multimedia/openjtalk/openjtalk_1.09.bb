SUMMARY = "Open JTalk is a Japanese text-to-speech system."
HOMEPAGE = "http://open-jtalk.sourceforge.net/"
SECTION = "libs"
LICENSE = "BSD"

LIC_FILES_CHKSUM = "file://COPYING;md5=ec9073c0ca40ff7c388b31cfd6a07a9e"

BBCLASSEXTEND = "native"

SRC_URI = "\
	http://downloads.sourceforge.net/open-jtalk/open_jtalk-${PV}.tar.gz \
	file://fix-mecab-tool.patch \
	"

SRC_URI:class-native = "\
	http://downloads.sourceforge.net/open-jtalk/open_jtalk-${PV}.tar.gz \
	"


SRC_URI[md5sum] = "5dfdbad432d892f044fb96129a524bfe"
SRC_URI[sha256sum] = "8ed79238d825fee1d9e0a1c6c8a89e2cc707189be1caa3fa79e8eb72436079d7"

DEPENDS = " hts-engine openjtalk-native "
DEPENDS:class-native = " hts-engine-native "

RDEPENDS:${PN} = " openjtalk-voicedata "
RDEPENDS:${PN}:class-native = " "

inherit autotools-brokensep

S = "${WORKDIR}/open_jtalk-${PV}"


EXTRA_OECONF = " \
 --with-hts-engine-header-path=${PKG_CONFIG_SYSROOT_DIR}/usr/include \
 --with-hts-engine-library-path=${PKG_CONFIG_SYSROOT_DIR}/usr/lib \
"

EXTRA_OECONF:class-native = " \
 --with-hts-engine-header-path=${includedir} \
 --with-hts-engine-library-path=${libdir} \
"

do_install:append:class-native() {
	install -m 755 mecab/src/mecab-dict-index ${D}${bindir}
}

FILES:${PN} += " ${datadir}/dic/* "

