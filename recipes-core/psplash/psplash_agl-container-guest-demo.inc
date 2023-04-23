FILESEXTRAPATHS:prepend := "${THISDIR}/agl-container-guest:"
SRC_URI:append = " file://psplash.env"

PACKAGECONFIG:append = " drm drm-lease"

PSPLASH_DRM_LEASE_ARGS = "--drm-lease lease"

do_install:append () {
	sed -i -e '/^\[Service\]/a EnvironmentFile=/etc/default/psplash' ${D}${systemd_system_unitdir}/psplash-start.service
    install -Dm644 ${WORKDIR}/psplash.env ${D}${sysconfdir}/default/psplash
}
