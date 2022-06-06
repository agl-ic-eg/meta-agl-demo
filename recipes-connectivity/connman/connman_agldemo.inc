do_install:append() {
    # Need to ignore eth1 in cluster demo setup
    if ${@bb.utils.contains('AGL_FEATURES', 'agl-demo-cluster-support', 'true', 'false', d)}; then
        sed -i 's/^\(NetworkInterfaceBlacklist=.*\)/\1,eth1/' ${D}${sysconfdir}/connman/main.conf
    fi
}
