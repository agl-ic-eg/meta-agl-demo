FILESEXTRAPATHS:prepend := "${THISDIR}/weston-ini-conf:"

SRC_URI += " \
    file://remote-output.cfg.in \
    file://weston.ini.kvm.in \
"

# Options for the user to change in local.conf
# e.g. REMOTING_OUTPUT_MODE = "1080x1488"
REMOTING_OUTPUT_MODE ??= "640x720@30"
REMOTING_OUTPUT_HOST ??= "192.168.10.3"
REMOTING_OUTPUT_PORT ??= "5005"

# Default app IDs for KVM guests
GUEST_VM1_IMAGE ?= "agl-ivi-demo-flutter"
GUEST_VM2_IMAGE ?= "agl-cluster-demo-flutter"

do_configure:append() {
    # Standalone cluster support version
    sed -e "s#host=.*#host=${REMOTING_OUTPUT_HOST}#" \
        -e "s#port=.*#port=${REMOTING_OUTPUT_PORT}#" \
        ${WORKDIR}/remote-output.cfg.in  > ${WORKDIR}/remote-output.cfg

    # KVM guest cluster version
    sed -e "s#host=.*#host=172.16.10.3#" \
        -e "s#port=.*#port=${REMOTING_OUTPUT_PORT}#" \
        ${WORKDIR}/remote-output.cfg.in  > ${WORKDIR}/remote-output-kvm.cfg
}

do_compile:append() {
    # Create regular and KVM remoting enabled versions of the default
    # portrait and landscape demo IVI configurations
    for c in weston.ini.default weston.ini.landscape; do
        cp ${WORKDIR}/$c ${WORKDIR}/${c}-remoting
        echo >> ${WORKDIR}/${c}-remoting
        cat ${WORKDIR}/remote-output.cfg >> ${WORKDIR}/${c}-remoting

        cp ${WORKDIR}/$c ${WORKDIR}/${c}-remoting-kvm
        echo >> ${WORKDIR}/${c}-remoting-kvm
        cat ${WORKDIR}/remote-output-kvm.cfg >> ${WORKDIR}/${c}-remoting-kvm
    done

    # Create a canned configuration for the a KVM host, filling in
    # the guest VM application ids to pin them to specific outputs.
    rm -f ${WORKDIR}/weston.ini.kvm
    sed -e "s/@GUEST_VM1_ID@/${GUEST_VM1_IMAGE}/g" \
        -e "s/@GUEST_VM2_ID@/${GUEST_VM2_IMAGE}/g" \
        ${WORKDIR}/weston.ini.kvm.in > ${WORKDIR}/weston.ini.kvm
}

do_install:append() {
    install -m 0644 ${WORKDIR}/weston.ini.default-remoting ${D}${weston_ini_dir}/
    install -m 0644 ${WORKDIR}/weston.ini.default-remoting-kvm ${D}${weston_ini_dir}/
    install -m 0644 ${WORKDIR}/weston.ini.landscape-remoting ${D}${weston_ini_dir}/
    install -m 0644 ${WORKDIR}/weston.ini.landscape-remoting-kvm ${D}${weston_ini_dir}/
    install -m 0644 ${WORKDIR}/weston.ini.kvm ${D}${weston_ini_dir}/
}

# remoting

PACKAGE_BEFORE_PN += "${PN}-remoting"
FILES:${PN}-remoting = "${weston_ini_dir}/weston.ini.default-remoting"
RPROVIDES:${PN}-remoting = "weston-ini"
ALTERNATIVE:${PN}-remoting = "weston.ini"
ALTERNATIVE_TARGET_${PN}-remoting = "${weston_ini_dir}/weston.ini.default-remoting"
ALTERNATIVE_PRIORITY_${PN}-remoting = "30"

# remoting-kvm

PACKAGE_BEFORE_PN += "${PN}-remoting-kvm"
FILES:${PN}-remoting-kvm = "${weston_ini_dir}/weston.ini.default-remoting-kvm"
RPROVIDES:${PN}-remoting-kvm = "weston-ini"
ALTERNATIVE:${PN}-remoting-kvm = "weston.ini"
ALTERNATIVE_TARGET_${PN}-remoting-kvm = "${weston_ini_dir}/weston.ini.default-remoting-kvm"
ALTERNATIVE_PRIORITY_${PN}-remoting-kvm = "35"

# landscape-remoting

PACKAGE_BEFORE_PN += "${PN}-landscape-remoting"
FILES:${PN}-landscape-remoting = "${weston_ini_dir}/weston.ini.landscape-remoting"
RPROVIDES:${PN}-landscape-remoting = "weston-ini"
ALTERNATIVE:${PN}-landscape-remoting = "weston.ini"
ALTERNATIVE_TARGET_${PN}-landscape-remoting = "${weston_ini_dir}/weston.ini.landscape-remoting"
ALTERNATIVE_PRIORITY_${PN}-landscape-remoting = "31"

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
