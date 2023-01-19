DISTRO_FEATURES:append = " virtualization"

BBMULTICONFIG = "agl-kvm-guest"

AGL_FEATURES:append = " agl-kvm"

# Override remote display network configuration to use KVM demo's
# network configuration
TRANSMITTER_OUTPUT_HOST = "172.16.10.3"

# Override cluster dashboard VIS server host for KVM demo's network
# configuration
CLUSTER_DEMO_VISS_HOSTNAME = "172.16.10.2"

include mc-conf-${BB_CURRENT_MC}.inc
