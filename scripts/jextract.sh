#!/bin/bash

set -eu -o errtrace

this_dir="$(cd $(dirname $0) && pwd)"
repo_root="$(cd $this_dir/.. && pwd)"

JEXTRACT_DIR=${JEXTRACT_DIR:-$repo_root/jextract-22}
export PATH=$JEXTRACT_DIR/bin:$PATH
LLVM_INSTALL_DIR=${LLVM_INSTALL_DIR:-$repo_root/../llvm-project/llvm-install}

echo "" > all_includes.h

shopt -s globstar
for file in $LLVM_INSTALL_DIR/include/mlir-c/**
do
  if [ ! -d $file ] && [[ $file != *"Python"* ]]; then
    INCLUDE=${file#$LLVM_INSTALL_DIR/include\/}
    echo "#include \"$INCLUDE\"" >> all_includes.h
  fi
done

OUTPUT_PATH=$repo_root/src/main/java

jextract -l :libMLIR-C.so \
  -t org.mlir.bindings all_includes.h \
  --header-class-name Bindings \
  -I$LLVM_INSTALL_DIR/include \
  --output $OUTPUT_PATH