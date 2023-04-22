FILESEXTRAPATHS:prepend := "${THISDIR}/agl-compositor-init-agl-container-guest:"

SRC_URI:append = " \
    file://agl-compositor.env \
    "

DRM_LEASE_DEVICE = "lease"

do_install:append() {
    install -Dm644 ${WORKDIR}/agl-compositor.env ${D}${sysconfdir}/default/agl-compositor
}

FILES:${PN} += "\
    ${sysconfdir}/default/agl-compositor \
    "
