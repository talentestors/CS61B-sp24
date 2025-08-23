#!/usr/bin/env python3
"""
跨平台 Java 编译文件清理工具（带白名单保护）
支持：Windows, macOS, Linux
功能：
  1. 清理 .class 文件（跳过白名单目录）
  2. 可选清理其他编译产物（JAR/WAR/target/build）
  3. 白名单目录保护（防止误删重要文件）
"""

import os
import sys
import shutil
import platform
import re
from pathlib import Path

# ======================
# 用户可配置区域
# ======================

# 定义白名单目录（这些目录下的 .class 文件不会被清理）
# 支持相对路径（相对于项目根目录）和 glob 模式
WHITELIST_DIRS = [
    'src/main/resources/**',  # 保护所有资源目录
    'lib/**',                 # 保护库目录
    'external_libs/**',       # 保护外部库目录
    '**/config/**',           # 保护所有配置目录
    '**/*.properties',        # 保护属性文件（即使在其他目录）
    '**/*.xml',               # 保护 XML 配置文件
    'library-sp24/**'
]

# 定义要清理的其他编译产物（可选）
CLEANUP_OPTIONS = {
    "JAR 文件": r"\.jar$",
    "WAR 文件": r"\.war$",
    "EAR 文件": r"\.ear$",
    "Maven target 目录": r"^target$",
    "Gradle build 目录": r"^build$"
}

# ======================
# 核心功能实现
# ======================

def print_header():
    """打印脚本标题和系统信息"""
    print("=" * 65)
    print("Java 编译文件清理工具 (跨平台版) - 带白名单保护")
    print("-" * 65)
    print(f"操作系统: {platform.system()} {platform.release()}")
    print(f"Python 版本: {platform.python_version()}")
    print(f"当前目录: {os.getcwd()}")
    print(f"白名单目录: {', '.join(WHITELIST_DIRS)}")
    print("=" * 65)

def is_in_whitelist(file_path):
    """
    检查文件是否在白名单中
    支持 glob 模式匹配
    """
    rel_path = os.path.relpath(file_path, os.getcwd()).replace(os.sep, '/')
    
    for pattern in WHITELIST_DIRS:
        # 处理 glob 模式
        if '*' in pattern or '?' in pattern:
            if re.search(fnmatch_to_regex(pattern), rel_path):
                return True
        # 精确路径匹配
        else:
            normalized_pattern = pattern.replace(os.sep, '/')
            if rel_path.startswith(normalized_pattern):
                return True
    
    return False

def fnmatch_to_regex(pattern):
    """将 glob 模式转换为正则表达式"""
    pattern = pattern.replace(os.sep, '/')
    pattern = re.escape(pattern)
    pattern = pattern.replace(r'\*\*', r'.*')
    pattern = pattern.replace(r'\*', r'[^/]*')
    pattern = pattern.replace(r'\?', r'.')
    return f"^{pattern}$"

def find_files(pattern, directory="."):
    """查找匹配模式的文件（跳过白名单）"""
    matches = []
    for root, _, files in os.walk(directory):
        for file in files:
            if re.search(pattern, file):
                file_path = Path(root) / file
                # 跳过白名单中的文件
                if not is_in_whitelist(str(file_path)):
                    matches.append(file_path)
    return matches

def find_directories(pattern, directory="."):
    """查找匹配模式的目录（跳过白名单）"""
    matches = []
    for root, dirs, _ in os.walk(directory):
        for dir_name in dirs:
            if re.search(pattern, dir_name):
                dir_path = Path(root) / dir_name
                # 跳过白名单中的目录
                if not is_in_whitelist(str(dir_path)):
                    matches.append(dir_path)
    return matches

def clean_class_files():
    """清理 .class 文件（跳过白名单）"""
    print("\n🔍 正在搜索 .class 文件 (跳过白名单目录)...")
    class_files = find_files(r"\.class$", ".")
    
    if not class_files:
        print("✅ 未找到需要清理的 .class 文件")
        return 0
    
    print(f"找到 {len(class_files)} 个可清理的 .class 文件:")
    for i, f in enumerate(class_files[:5], 1):
        print(f"  {i}. {os.path.relpath(f, os.getcwd())}")
    if len(class_files) > 5:
        print(f"  ... 以及 {len(class_files) - 5} 个其他文件")
    
    return len(class_files)

def clean_additional_files():
    """清理其他编译产物（跳过白名单）"""
    results = {}
    
    # 清理文件
    for desc, pattern in CLEANUP_OPTIONS.items():
        if "目录" in desc:  # 目录类型
            files = find_directories(pattern, ".")
        else:  # 文件类型
            files = find_files(pattern, ".")
        
        if files:
            results[desc] = files
    
    return results

def confirm_deletion(file_count, additional_files):
    """确认删除操作"""
    total = file_count + sum(len(files) for files in additional_files.values())
    
    if total == 0:
        print("\n🎉 无需清理 - 未找到任何可清理的编译产物")
        return False
    
    print("\n" + "=" * 65)
    print(f"准备删除 {total} 个编译产物 (白名单已保护):")
    print(f"  • {file_count} 个 .class 文件")
    
    for desc, files in additional_files.items():
        print(f"  • {len(files)} 个 {desc}")
    
    print("=" * 65)
    
    while True:
        response = input("\n确认删除这些文件吗? [y/N]: ").strip().lower()
        if response in ('y', 'yes'):
            return True
        elif response in ('n', 'no', ''):  # 默认按N处理
            return False
        else:
            print("  请输入 y 或 n")

def perform_cleanup(file_count, additional_files):
    """执行清理操作"""
    # 删除 .class 文件
    if file_count > 0:
        class_files = find_files(r"\.class$", ".")
        for f in class_files:
            try:
                os.remove(f)
                print(f"  • 已删除: {os.path.relpath(f, os.getcwd())}")
            except Exception as e:
                print(f"  ✘ 删除失败 {f}: {str(e)}")
    
    # 删除其他文件/目录
    for desc, files in additional_files.items():
        for f in files:
            try:
                if os.path.isdir(f):
                    shutil.rmtree(f)
                    print(f"  • 已删除目录: {os.path.relpath(f, os.getcwd())}")
                else:
                    os.remove(f)
                    print(f"  • 已删除: {os.path.relpath(f, os.getcwd())}")
            except Exception as e:
                print(f"  ✘ 删除失败 {f}: {str(e)}")
    
    print("\n✅ 清理完成! (白名单目录已保护)")
    return True

def main():
    """主执行函数"""
    print_header()
    
    # 查找 .class 文件（跳过白名单）
    class_count = clean_class_files()
    
    # 查找其他编译产物（跳过白名单）
    additional_files = clean_additional_files()
    
    # 确认并执行清理
    if confirm_deletion(class_count, additional_files):
        perform_cleanup(class_count, additional_files)
    else:
        print("\n清理操作已取消")

if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        print("\n\n操作已中断")
        sys.exit(1)
    except Exception as e:
        print(f"\n❌ 发生错误: {str(e)}")
        sys.exit(1)
