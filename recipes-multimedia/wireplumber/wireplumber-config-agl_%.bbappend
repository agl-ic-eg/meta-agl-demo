FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://30-v4l2-monitor.lua"

do_install:append() {
    # override the one from meta-agl to be able to set as default USB camera
    # such that camera-gstreamer and implicitly pipewire work out of the box
    install -D -m 0644 ${WORKDIR}/30-v4l2-monitor.lua ${D}${sysconfdir}/wireplumber/host.lua.d/
}

