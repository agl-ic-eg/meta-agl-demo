SUMMARY = "Custom Qt5 Python Widgets"
HOMEPAGE = "https://github.com/pythonguis/python-qtwidgets"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=dc687cb5e5291e09b4f52b0b182106fe"

SRC_URI[sha256sum] = "97b8373844788a22c836f5a16a1e60c9ef1469bfc4aedc09ae786fc9aa0b49df"

inherit pypi setuptools3

RDEPENDS:${PN} += " \
    python3-qtpy \
"
