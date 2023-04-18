SUMMARY = "Python client for KUKSA.val, the KUKSA Vehicle Abstraction Layer"
HOMEPAGE = "https://github.com/eclipse/kuksa.val"
BUGTRACKER = "https://github.com/eclipse/kuksa.val/issues"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=2b42edef8fa55315f34f2370b4715ca9"

DEPENDS = " \
    python3-setuptools-git-versioning-native \
    python3-grpcio-tools-native \
    python3-grpcio \
"

require kuksa-val.inc

SRC_URI += "file://0001-kuksa-client-Update-cmd2-completer-usage.patch;patchdir=.. \
            file://0002-kuksa-client-Add-external-certificates-support.patch;patchdir=.. \
"

S = "${WORKDIR}/git/kuksa-client"

inherit python_setuptools_build_meta

RDEPENDS:${PN} += " \
    python3-cmd2 \
    python3-importlib-metadata \
    python3-pkg-resources \
    python3-pygments \
    python3-websockets \
    python3-grpcio \
    python3-grpcio-tools \
    python3-jsonpath-ng \
"

# A script for generating new certificates currently gets shipped inside
# the client module, for now add bash to RDEPENDS to quiet the QA error.
# This should probably be addressed with finer-grained packaging or some
# other change worked out with upstream.
RDEPENDS:${PN} += "bash"
