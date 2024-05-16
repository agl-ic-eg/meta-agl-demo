SUMMARY = "AGL gateway demo image"

LICENSE = "MIT"

require recipes-platform/images/agl-image-minimal.bb
require agl-demo-features.inc

IMAGE_FEATURES += " \
    kuksa-val-databroker \
    ssh-server-openssh \
    ${@bb.utils.contains('DISTRO_FEATURES', 'agl-devel', 'can-test-tools' , '', d)} \
"

AGL_DEVEL_INSTALL = " \
    packagegroup-agl-kuksa-val-databroker-devel \
    tcpdump \
"

IMAGE_INSTALL += " \
    agl-vss-proxy \
    kuksa-dbc-feeder-conf-gw-control-panel \
    vss-agl-gw-control-panel \
    ${@bb.utils.contains('DISTRO_FEATURES', 'agl-devel', '${AGL_DEVEL_INSTALL}', '', d)} \
"
