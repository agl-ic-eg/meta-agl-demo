# add missing dependencies for SDK

RDEPENDS:${PN} += " \
	qttools-plugins \
	qtquickcontrols-dev \
	qtquickcontrols-qmlplugins \
	qtquickcontrols2-dev \
	qtquickcontrols2-mkspecs \
	qtquickcontrols2-qmlplugins \
"

# remove dependency on qtwebkit (still added to SDK by packagegroup-qt5-toolchain-target)
# see SPEC-1159
RDEPENDS:${PN}:remove = " \
    qtwebkit-dev \
    qtwebkit-mkspecs \
    qtwebkit-qmlplugins \
"
