require recipes-platform/images/agl-image-weston.bb

SUMMARY = "AGL demo control panel image"

require agl-demo-features.inc

IMAGE_FEATURES += "splash package-management ssh-server-openssh"

# NOTE: The client key and certificate in kuksa-certificates-agl-client
#       seem required by kuksa-client at the moment even though client
#       certification has been deprecated upstream, this needs further
#       investigation so it can be dropped from the image.
#   
IMAGE_KUKSA_PACKAGES = " \
    kuksa-client \
    kuksa-certificates-agl-ca \
    kuksa-certificates-agl-client \
"

IMAGE_INSTALL += "\
    packagegroup-agl-networking \
    weston-ini-conf-landscape \
    weston-agl-driver-conf \
    ${IMAGE_KUKSA_PACKAGES} \
    agl-demo-control-panel \
    qtwayland \
    qtwayland-plugins \
    qtwayland-qmlplugins \
"
