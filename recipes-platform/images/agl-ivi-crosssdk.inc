#
# Common IVI SDK configuration
#
# Only host tool additions should done here
#

# Add wayland-scanner to SDK (SPEC-945)
# Use TOOLCHAIN_HOST_TASK instead of adding to the packagegroup
# wayland-scanner is in nativesdk-wayland-dev !
# option: add also nativesdk-qtwayland-tools
TOOLCHAIN_HOST_TASK += "nativesdk-wayland nativesdk-wayland-dev"

TOOLCHAIN_HOST_TASK += "nativesdk-perl-modules"

# add grpc for building natively for grpc_cpp_plugin
TOOLCHAIN_HOST_TASK += "nativesdk-grpc nativesdk-grpc-dev"

# Add gcc-sanitizers to support building applications using the SDK with
# AddressSanitizer support to detect use-after-frees along with other
# memory issue.
TOOLCHAIN_SANITIZERS_TASKS = "gcc-sanitizers"
# gcc-sanitizers is currently (scarthgap) not supported on RISC-V
TOOLCHAIN_SANITIZERS_TASKS:riscv64 = ""
TOOLCHAIN_TARGET_TASK += "${TOOLCHAIN_SANITIZERS_TASKS}"
