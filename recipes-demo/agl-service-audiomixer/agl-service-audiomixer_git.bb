SUMMARY     = "Audio Mixer Service Daemon"
DESCRIPTION = "AGL Audio Mixer Service Daemon"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/agl-service-audiomixer"
SECTION     = "apps"
LICENSE     = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;beginline=3;md5=e8ad01a5182f2c1b3a2640e9ea268264"

DEPENDS = "boost openssl nlohmann-json systemd pipewire wireplumber"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/agl-service-audiomixer.git;protocol=https;branch=${AGL_BRANCH} \
           file://agl-service-audiomixer.conf \
           file://agl-service-audiomixer.token \
"
SRCREV  = "${AGL_APP_REVISION}"

PV = "2.0+git${SRCPV}"
S  = "${WORKDIR}/git"

inherit meson pkgconfig systemd

SYSTEMD_SERVICE:${PN} = "agl-service-audiomixer.service" 

do_install:append() {
    # Currently using default global client and CA certificates
    # for KUKSA.val SSL, installing app specific ones would go here.

    # VIS authorization token file for KUKSA.val should ideally not
    # be readable by other users, but currently that's not doable
    # until a packaging/sandboxing/MAC scheme is (re)implemented or
    # something like OAuth is plumbed in as an alternative.
    install -d ${D}${sysconfdir}/xdg/AGL/agl-service-audiomixer
    install -m 0644 ${WORKDIR}/agl-service-audiomixer.conf ${D}${sysconfdir}/xdg/AGL/
    install -m 0644 ${WORKDIR}/agl-service-audiomixer.token ${D}${sysconfdir}/xdg/AGL/agl-service-audiomixer/
}

FILES:${PN} += "${systemd_system_unitdir}"

RDEPENDS:${PN} += "kuksa-val"
