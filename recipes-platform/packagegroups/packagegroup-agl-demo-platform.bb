SUMMARY = "The software for DEMO platform of AGL IVI profile"
DESCRIPTION = "A set of packages belong to AGL Demo Platform"

LICENSE = "MIT"

inherit packagegroup

PROVIDES = "${PACKAGES}"
PACKAGES = "\
    packagegroup-agl-demo-platform \
    packagegroup-agl-demo-platform-devel \
    "

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} += "\
    packagegroup-agl-image-ivi \
    "

RDEPENDS:${PN} += "\
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

# Cluster demo support.
CLUSTER_SUPPORT_PACKAGES = " \
	tbtnavi \
	cluster-demo-network-config \
"
CLUSTER_SUPPORT = "${@bb.utils.contains("AGL_FEATURES", "agl-demo-cluster-support", "${CLUSTER_SUPPORT_PACKAGES}", "",d)}"

# Hook for demo platform configuration
# ATM used for:
# 1) Adding udev configuration and scripts for supporting USB attached
#    I2C devices for RTC and HVAC LED support.
DEMO_UNIT_CONF ?= "demo-i2c-udev-conf"

# Preload only if agl-demo-preload is set
DEMO_PRELOAD = "${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "${DEMO_UNIT_CONF}", "",d)}"

RDEPENDS:${PN}:append = " \
    launcher \
    qtquickcontrols2-agl \
    qtquickcontrols2-agl-style \
    ${@bb.utils.contains('DISTRO_FEATURES', 'agl-devel', 'unzip mpc' , '', d)} \
    ${AGL_APPS} \
    ${CLUSTER_SUPPORT} \
    ${DEMO_PRELOAD} \
    "
