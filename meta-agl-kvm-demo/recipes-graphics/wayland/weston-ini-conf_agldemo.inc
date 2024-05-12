FILESEXTRAPATHS:prepend := "${THISDIR}/weston-ini-conf:"

SRC_URI += "file://weston.ini.kvm.in"

REMOTING_KVM_OUTPUT_HOST ?= "172.16.10.3"

# Default app IDs for KVM guests
GUEST_VM1_ID ?= "agl-ivi-demo"
GUEST_VM2_ID ?= "agl-cluster-demo"

do_configure:append() {
    # KVM guest cluster version
    sed -e "s#host=.*#host=${REMOTING_KVM_OUTPUT_HOST}#" \
        -e "s#port=.*#port=${REMOTING_OUTPUT_PORT}#" \
        ${WORKDIR}/remote-output.cfg.in  > ${WORKDIR}/remote-output-kvm.cfg
}

do_compile:append() {
    # Create KVM remoting enabled versions of the default portrait
    # and landscape demo IVI configurations
    for c in weston.ini.default weston.ini.landscape; do
        cp ${WORKDIR}/$c ${WORKDIR}/${c}-remoting-kvm
        echo >> ${WORKDIR}/${c}-remoting-kvm
        cat ${WORKDIR}/remote-output-kvm.cfg >> ${WORKDIR}/${c}-remoting-kvm
    done

    # Create a canned configuration for the a KVM host, filling in
    # the guest VM application ids to pin them to specific outputs.
    rm -f ${WORKDIR}/weston.ini.kvm
    sed -e "s/@GUEST_VM1_ID@/${GUEST_VM1_ID}/g" \
        -e "s/@GUEST_VM2_ID@/${GUEST_VM2_ID}/g" \
        ${WORKDIR}/weston.ini.kvm.in > ${WORKDIR}/weston.ini.kvm
}

do_install:append() {
    install -m 0644 ${WORKDIR}/weston.ini.default-remoting-kvm ${D}${weston_ini_dir}/
    install -m 0644 ${WORKDIR}/weston.ini.landscape-remoting-kvm ${D}${weston_ini_dir}/
    install -m 0644 ${WORKDIR}/weston.ini.kvm ${D}${weston_ini_dir}/
}

# remoting-kvm

PACKAGE_BEFORE_PN += "${PN}-remoting-kvm"
FILES:${PN}-remoting-kvm = "${weston_ini_dir}/weston.ini.default-remoting-kvm"
RPROVIDES:${PN}-remoting-kvm = "weston-ini"
ALTERNATIVE:${PN}-remoting-kvm = "weston.ini"
ALTERNATIVE_TARGET_${PN}-remoting-kvm = "${weston_ini_dir}/weston.ini.default-remoting-kvm"
ALTERNATIVE_PRIORITY_${PN}-remoting-kvm = "35"

# landscape-remoting-kvm

PACKAGE_BEFORE_PN += "${PN}-landscape-remoting-kvm"
FILES:${PN}-landscape-remoting-kvm = "${weston_ini_dir}/weston.ini.landscape-remoting-kvm"
RPROVIDES:${PN}-landscape-remoting-kvm = "weston-ini"
ALTERNATIVE:${PN}-landscape-remoting-kvm = "weston.ini"
ALTERNATIVE_TARGET_${PN}-landscape-remoting-kvm = "${weston_ini_dir}/weston.ini.landscape-remoting-kvm"
ALTERNATIVE_PRIORITY_${PN}-landscape-remoting-kvm = "36"

# kvm

PACKAGE_BEFORE_PN += "${PN}-kvm"
FILES:${PN}-kvm = "${weston_ini_dir}/weston.ini.kvm"
RPROVIDES:${PN}-kvm = "weston-ini"
ALTERNATIVE:${PN}-kvm = "weston.ini"
ALTERNATIVE_TARGET_${PN}-kvm = "${weston_ini_dir}/weston.ini.kvm"
ALTERNATIVE_PRIORITY_${PN}-kvm = "40"
