SUMMARY = "Abstraction layer for PyQt5/PySide2/PyQt6/PySide6"
HOMEPAGE = "https://github.com/spyder-ide/qtpy"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=b2830f54500be1314b9ec6096989f983"

PYPI_PACKAGE = "QtPy"

SRC_URI[sha256sum] = "db2d508167aa6106781565c8da5c6f1487debacba33519cedc35fa8997d424d4"

inherit pypi setuptools3

RDEPENDS:${PN} += " \
    python3-pyqt5 \
"
