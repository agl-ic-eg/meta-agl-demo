FILESEXTRAPATHS:prepend := "${THISDIR}/weston-ini-conf:"

SRC_URI += "file://weston.ini.kvm.in"

GUEST_VM1_IMAGE ?= "agl-ivi-demo-flutter"
GUEST_VM2_IMAGE ?= "agl-cluster-demo-flutter"

do_compile:append() {
    # Start with a canned configuration for a KVM host, filling in
    # the guest VM application ids to pin them to specific outputs.
    rm -f ${WORKDIR}/weston.ini.kvm
    sed -e "s/@GUEST_VM1_IMAGE@/${GUEST_VM1_IMAGE}/g" \
        -e "s/@GUEST_VM2_IMAGE@/${GUEST_VM2_IMAGE}/g" \
        ${WORKDIR}/weston.ini.kvm.in > ${WORKDIR}/weston.ini.kvm
}

do_install:append() {
    install -m 0644 ${WORKDIR}/weston.ini.kvm ${D}${weston_ini_dir}/
}

PACKAGE_BEFORE_PN += "${PN}-kvm"

FILES:${PN}-kvm = "${weston_ini_dir}/weston.ini.kvm"

RPROVIDES:${PN}-kvm = "weston-ini"
RCONFLICTS:${PN}-kvm = "${PN}"
ALTERNATIVE:${PN}-kvm = "weston.ini"
ALTERNATIVE_TARGET_${PN}-kvm = "${weston_ini_dir}/weston.ini.kvm"
