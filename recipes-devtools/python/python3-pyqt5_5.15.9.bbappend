# Enable SVG support
DEPENDS:append = " qtsvg"
PYQT_MODULES:append = " QtSvg"

# Fix python interpreter paths in pyrcc5, etc., adapted from newer upstream
# recipe
do_install:append() {
    sed -i "s,^exec .*python${PYTHON_BASEVERSION},exec ${bindir}/python3," ${D}/${bindir}/*
}
