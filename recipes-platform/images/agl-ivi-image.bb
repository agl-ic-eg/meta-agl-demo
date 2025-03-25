SUMMARY = "AGL IVI demo base image"
LICENSE = "MIT"

require recipes-platform/images/agl-image-compositor.bb

require agl-ivi-demo-features.inc
require agl-demo-container-guest-integration.inc

IMAGE_FEATURES += "splash package-management ssh-server-openssh"

AGL_DEVEL_INSTALL += "\
    simple-can-simulator \
    unzip \
    mpc \
"

AGL_APPS_INSTALL = ""

PLATFORM_SERVICES_INSTALL = " \
    packagegroup-agl-ivi-multimedia-platform \
    packagegroup-agl-ivi-services-platform \
    agl-service-audiomixer-systemd-databroker \
    agl-service-hvac-systemd-databroker \
"

IMAGE_INSTALL += " \
    packagegroup-agl-ivi-connectivity \
    packagegroup-agl-ivi-graphics \
    packagegroup-agl-ivi-multimedia-client \
    packagegroup-agl-ivi-multimedia-hardware \
    packagegroup-agl-ivi-navigation \
    packagegroup-agl-ivi-identity \
    packagegroup-agl-ivi-services-applaunchd \
    ${PLATFORM_SERVICES_INSTALL} \
    iproute2 \
    ${AGL_APPS_INSTALL} \
    ${@bb.utils.contains("DISTRO_FEATURES", "agl-devel", "${AGL_DEVEL_INSTALL}" , "", d)} \
"

