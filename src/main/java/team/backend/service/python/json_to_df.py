import pandas as pd

def json_to_dataframe(json_file):
    try:
        # JSON 파일을 DataFrame으로 읽기
        df = pd.read_json(json_file)
        return df
    except Exception as e:
        print(f"Error occurred while reading JSON file: {e}")
        return None