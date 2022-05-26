SUMMARY = "An implementation of the CAN SAE J1939 standard for Python."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=32e7309a8200a5f4b7aec6efcfb8e77e"
PYPI_PACKAGE = "can-j1939"

SRC_URI[sha256sum] = "beda5bf2e2502c4a7d97a989075c56b98e33e49b619ef8054ed13aca911be3d3"

inherit pypi setuptools3

RDEPENDS:${PN} += " \
    python3-can \
    python3-numpy \
"
