{
  description = "Java 21 + IDEA devshell";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
  };

  outputs =
    inputs:
    let
      system = "x86_64-linux";
      pkgs = import inputs.nixpkgs {
        inherit system;
        config.allowUnfree = true;
      };
    in
    {
      devShells.${system}.default = pkgs.mkShell {
        packages = [
          pkgs.jdk21
          pkgs.intellij-idea
          pkgs.opencode-desktop
        ];

        shellHook = ''
          export JAVA_HOME="${pkgs.jdk21}"
        '';
      };
    };
}
