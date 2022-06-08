SUMMARY = "The software for DEMO platform of AGL IVI profile"
DESCRIPTION = "A set of packages belong to AGL Demo Platform"

LICENSE = "MIT"

inherit packagegroup

PROVIDES = "${PACKAGES}"
PACKAGES = "\
    packagegroup-agl-demo-platform \
    "

RDEPENDS:${PN} += "\
    packagegroup-agl-image-ivi \
    packagegroup-agl-profile-graphical-qt5 \
    packagegroup-hmi-framework \
    packagegroup-agl-demo \
    "

AGL_APPS = " \
    dashboard \
    hvac \
    ondemandnavi \
    settings \
    mediaplayer \
    messaging \
    phone \
    radio \
    "

RDEPENDS:${PN}:append = " \
    launcher \
    qtquickcontrols2-agl \
    qtquickcontrols2-agl-style \
    ${@bb.utils.contains('DISTRO_FEATURES', 'agl-devel', 'unzip mpc' , '', d)} \
    ${AGL_APPS} \
    psplash-portrait-config \
    "
