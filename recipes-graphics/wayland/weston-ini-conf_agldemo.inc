FILESEXTRAPATHS:prepend := "${THISDIR}/weston-ini-conf:"

SRC_URI += "file://weston.ini.kvm.in"

GUEST_VM1_IMAGE ?= "agl-ivi-demo-platform-flutter"
GUEST_VM2_IMAGE ?= "agl-cluster-demo-platform-flutter"

do_compile:append() {
    # Put all of our cfg files together for a default portrait
    # orientation configuration
    rm -f ${WORKDIR}/weston.ini.flutter
    for F in ${WESTON_FRAGMENTS}; do
        cat ${WORKDIR}/${F}.cfg >> ${WORKDIR}/weston.ini.flutter
	if grep -q '\[output\]' ${WORKDIR}/${F}.cfg; then
            echo "activation-area=1080x1600+0,160" >> ${WORKDIR}/weston.ini.flutter
        fi
        echo >> ${WORKDIR}/weston.ini.flutter
    done
    sed -i -e '$ d' ${WORKDIR}/weston.ini.flutter

    # Start with a canned configuration for a KVM host, filling in
    # the guest VM application ids to pin them to specific outputs.
    rm -f ${WORKDIR}/weston.ini.kvm
    sed -e "s/@GUEST_VM1_IMAGE@/${GUEST_VM1_IMAGE}/g" \
        -e "s/@GUEST_VM2_IMAGE@/${GUEST_VM2_IMAGE}/g" \
        ${WORKDIR}/weston.ini.kvm.in > ${WORKDIR}/weston.ini.kvm
}

do_install:append() {
    install -m 0644 ${WORKDIR}/weston.ini.flutter ${D}${weston_ini_dir}/
    install -m 0644 ${WORKDIR}/weston.ini.kvm ${D}${weston_ini_dir}/
}

PACKAGE_BEFORE_PN += "${PN}-flutter ${PN}-kvm"

FILES:${PN}-flutter = "${weston_ini_dir}/weston.ini.flutter"

RPROVIDES:${PN}-flutter = "weston-ini"
RCONFLICTS:${PN}-flutter = "${PN}"
ALTERNATIVE:${PN}-flutter = "weston.ini"
ALTERNATIVE_TARGET_${PN}-flutter = "${weston_ini_dir}/weston.ini.flutter"

FILES:${PN}-kvm = "${weston_ini_dir}/weston.ini.kvm"

RPROVIDES:${PN}-kvm = "weston-ini"
RCONFLICTS:${PN}-kvm = "${PN}"
ALTERNATIVE:${PN}-kvm = "weston.ini"
ALTERNATIVE_TARGET_${PN}-kvm = "${weston_ini_dir}/weston.ini.kvm"
