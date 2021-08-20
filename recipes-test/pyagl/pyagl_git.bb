SUMMARY = "Python bindings and tests for Automotive Grade Linux services"
HOMEPAGE = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/src/pyagl"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/src/pyagl;protocol=https;branch=${AGL_BRANCH}"
SRCREV = "137a32d3b0068e2141ed9aab23d8a8956c0cb094"
PV = "${AGL_BRANCH}+git${SRCPV}"

S = "${WORKDIR}/git"

inherit setuptools3

do_install:append() {
    install -D -m 0755 ${S}/pyagl/scripts/pyagl ${D}${bindir}/pyagl
}

RDEPENDS:${PN} += " \
    python3-asyncio \
    python3-asyncssh \
    python3-core \
    python3-json \
    python3-logging \
    python3-math \
    python3-parse \
    python3-pprint \
    python3-pytest \
    python3-pytest-asyncio \
    python3-pytest-dependency \
    python3-pytest-reverse \
    python3-typing \
    python3-websockets \
"
