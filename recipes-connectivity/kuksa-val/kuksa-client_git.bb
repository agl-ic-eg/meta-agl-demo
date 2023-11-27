SUMMARY = "Python client for KUKSA.val, the KUKSA Vehicle Abstraction Layer"
HOMEPAGE = "https://github.com/eclipse/kuksa.val"
BUGTRACKER = "https://github.com/eclipse/kuksa.val/issues"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

DEPENDS = " \
    python3-setuptools-git-versioning-native \
    python3-grpcio-tools-native \
    python3-grpcio \
"

PV = "0.4.2"

SRC_URI = "gitsm://github.com/eclipse-kuksa/kuksa-python-sdk.git;protocol=https;branch=main \
           file://0001-kuksa-client-Update-cmd2-completer-usage.patch;patchdir=.. \
           file://0002-kuksa-client-Add-external-certificates-support.patch;patchdir=.. \
"
SRCREV = "4ac107a638c8dbf5cde1743a3df2e28589990332"

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
