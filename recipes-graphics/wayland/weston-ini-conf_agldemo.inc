FILESEXTRAPATHS:prepend := "${THISDIR}/weston-ini-conf:"

SRC_URI += "file://remote-output.cfg.in"

WESTON_FRAGMENTS_BASE += "grpc-proxy"

do_configure:append() {
    # Standalone cluster support version
    cp ${WORKDIR}/remote-output.cfg.in ${WORKDIR}/remote-output.cfg
}

do_compile:append() {
    # Create remoting enabled versions of the default portrait
    # and landscape demo IVI configurations
    for c in weston.ini.default weston.ini.landscape; do
        cp ${WORKDIR}/$c ${WORKDIR}/${c}-remoting
        echo >> ${WORKDIR}/${c}-remoting
        cat ${WORKDIR}/remote-output.cfg >> ${WORKDIR}/${c}-remoting
    done
}

do_install:append() {
    install -m 0644 ${WORKDIR}/weston.ini.default-remoting ${D}${weston_ini_dir}/
    install -m 0644 ${WORKDIR}/weston.ini.landscape-remoting ${D}${weston_ini_dir}/
}

# remoting

PACKAGE_BEFORE_PN += "${PN}-remoting"
FILES:${PN}-remoting = "${weston_ini_dir}/weston.ini.default-remoting"
RPROVIDES:${PN}-remoting = "weston-ini"
ALTERNATIVE:${PN}-remoting = "weston.ini"
ALTERNATIVE_TARGET_${PN}-remoting = "${weston_ini_dir}/weston.ini.default-remoting"
ALTERNATIVE_PRIORITY_${PN}-remoting = "30"

# landscape-remoting

PACKAGE_BEFORE_PN += "${PN}-landscape-remoting"
FILES:${PN}-landscape-remoting = "${weston_ini_dir}/weston.ini.landscape-remoting"
RPROVIDES:${PN}-landscape-remoting = "weston-ini"
ALTERNATIVE:${PN}-landscape-remoting = "weston.ini"
ALTERNATIVE_TARGET_${PN}-landscape-remoting = "${weston_ini_dir}/weston.ini.landscape-remoting"
ALTERNATIVE_PRIORITY_${PN}-landscape-remoting = "31"
