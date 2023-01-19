SUMMARY     = "Setting files for QEMU networking for guest VMs"
LICENSE     = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit systemd allarch

SRC_URI = "file://vmnet0.netdev \
           file://vmnet0.network \
	   file://bridge.conf \
	   file://dnsmasq-qemu.conf \
	   file://connman-nodnsproxy.conf \
"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

QEMU_IMAGE = "agl-cluster-demo-platform"
QEMU_UNIT = "agl-qemu-runner@${QEMU_IMAGE}.service"

do_install() {
    # Install systemd-networkd vmnet0 configuration
    install -d ${D}${systemd_unitdir}/network
    install -m 0644 ${WORKDIR}/vmnet0.netdev ${D}${systemd_unitdir}/network/
    install -m 0644 ${WORKDIR}/vmnet0.network ${D}${systemd_unitdir}/network/

    # Install QEMU bridge configuration
    install -d ${D}${sysconfdir}/qemu
    install -m 0644 ${WORKDIR}/bridge.conf ${D}${sysconfdir}/qemu/

    # Configure dnsmasq to serve DHCP to the guests
    install -d ${D}${sysconfdir}/dnsmasq.d
    install -m 0644 ${WORKDIR}/dnsmasq-qemu.conf ${D}${sysconfdir}/dnsmasq.d/

    # Disable ConnMan's local DNS proxy to not conflict with dnsmasq
    install -d ${D}${systemd_system_unitdir}/connman.service.d/
    install -m 0644 ${WORKDIR}/connman-nodnsproxy.conf ${D}${systemd_system_unitdir}/connman.service.d/    
}

FILES:${PN} += "${systemd_unitdir}/network ${systemd_system_unitdir}"

RDEPENDS:${PN} += "agl-qemu-runner dnsmasq connman"
