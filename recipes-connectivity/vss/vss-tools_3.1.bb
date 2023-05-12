SUMMARY = "COVESA Vehicle Signal Specification tooling."
HOMEPAGE = "https://github.com/COVESA/vss-tools"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9741c346eef56131163e13b9db1241b3"

SRC_URI = "git://github.com/COVESA/vss-tools.git;protocol=https;branch=master"
SRCREV = "97148190d0f84bedfc933b8bc4391c0a7e7a099b"

S = "${WORKDIR}/git"

inherit setuptools3

RDEPENDS:${PN} += " \
    python3-core \
    python3-ctypes \
    python3-email \
    python3-json \
    python3-logging \
    python3-netclient \
    python3-pkg-resources \
    python3-anytree \
    python3-deprecation \
    python3-graphql-core \
    python3-pyyaml \
    python3-six \
"

BBCLASSEXTEND += "native nativesdk"
