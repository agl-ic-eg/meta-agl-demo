SUMMARY = " A collection of tools for dealing with Chromium development"
DESCRIPTION = "The Chromium depot_tools suite contains many tools to assist/augment the Chromium development environment."
HOMEPAGE = "https://commondatastorage.googleapis.com/chrome-infra-docs/flat/depot_tools/docs/html/depot_tools_tutorial.html"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c2c05f9bdd5fc0b458037c2d1fb8d95e"

SRC_URI = "git://chromium.googlesource.com/chromium/tools/depot_tools.git;protocol=https;branch=main"
SRCREV = "64b61755572b1d03b3a43f1a29b617dcae3a3fe0"

S = "${WORKDIR}/git"

inherit native

do_compile[network] = "1"
do_compile() {
    export PATH=${S}:$PATH
    ./ensure_bootstrap
}

do_install() {
    install -d ${D}${datadir}/depot_tools
    cp -aR --no-dereference --no-preserve=owner ${WORKDIR}/git/. ${D}${datadir}/depot_tools
}

INSANE_SKIP:${PN} += "already-stripped file-rdeps"

BBCLASSEXTEND = "native"

FILES:${PN}-dev += " \
  ${datadir}/depot_tools/* \
"
