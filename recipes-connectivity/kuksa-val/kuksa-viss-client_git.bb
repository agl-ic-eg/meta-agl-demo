SUMMARY = "Python client for KUKSA.val, the KUKSA Vehicle Abstraction Layer"
HOMEPAGE = "https://github.com/eclipse/kuksa.val"
BUGTRACKER = "https://github.com/eclipse/kuksa.val/issues"

LICENSE = "EPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d9fc0efef5228704e7f5b37f27192723"

DEPENDS = "python3-setuptools-git-versioning-native"

require kuksa-val.inc

inherit setuptools3

RDEPENDS:${PN} += " \
    python3-cmd2 \
    python3-importlib-metadata \
    python3-pkg-resources \
    python3-pygments \
    python3-websockets \
"

# A script for generating new certificates currently gets shipped inside
# the client module, for now add bash to RDEPENDS to quiet the QA error.
# This should probably be addressed with finer-grained packaging or some
# other change worked out with upstream.
RDEPENDS:${PN} += "bash"
