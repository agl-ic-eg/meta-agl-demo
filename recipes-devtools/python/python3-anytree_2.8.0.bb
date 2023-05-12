SUMMARY = "Powerful and Lightweight Python Tree Data Structure"
HOMEPAGE = "https://github.com/c0fec0de/anytree"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

PYPI_PACKAGE = "anytree"

SRC_URI[sha256sum] = "3f0f93f355a91bc3e6245319bf4c1d50e3416cc7a35cc1133c1ff38306bbccab"

inherit pypi setuptools3

do_install:append () {
    rm -f ${D}${prefix}/LICENSE
}

BBCLASSEXTEND += "native nativesdk"
