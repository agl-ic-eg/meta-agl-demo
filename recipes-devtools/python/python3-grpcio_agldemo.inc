# For some reason the atomics presence test in the grpc build
# passes on RISC-V even though linking with libatomic is actually
# needed. There are some mentions on the web of some atomics
# support incorrectly being in the libc stub on RISC-V, so a
# fix could be tracking down if that is the case and if it has
# been fixed in newer libc. Otherwise, the compile test in grpc
# will need to be changed to some other function that is known to
# only be in libatomic on RISC-V.
#
# The next problem is that setting GRPC_PYTHON_LDFLAGS to any value
# ends up not adding linking with pthread due to the way the logic
# is set up, so we need to actually add it explicitly. The upstream
# recipe seems slightly broken in this respect.

GRPC_LDFLAGS ?= " -lpthread"
GRPC_LDFLAGS:append:riscv64 = " -latomic"
export GRPC_PYTHON_LDFLAGS = "${GRPC_LDFLAGS}"
