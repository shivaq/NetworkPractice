def func():
    print("func() in toBeImported.py")

print("top-level in toBeImported.py")

if __name__ == "__main__":
    print("toBeImported.py is executed directly")
else:
    print("toBeImported.py is being imported into another module")
