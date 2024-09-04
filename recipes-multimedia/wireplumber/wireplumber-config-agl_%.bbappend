FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://30-AGL-demo-v4l2.conf"

do_install:append() {
    install -D -m 0644 ${WORKDIR}/30-AGL-demo-v4l2.conf ${D}${sysconfdir}/wireplumber/wireplumber.conf.d/
}

